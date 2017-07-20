
package cz.hartrik.sg2.app.sandbox;

import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.app.extension.io.*;
import cz.hartrik.sg2.app.module.*;
import cz.hartrik.sg2.app.module.about.AboutSubmodule;
import cz.hartrik.sg2.app.module.edit.*;
import cz.hartrik.sg2.app.module.io.*;
import cz.hartrik.sg2.app.module.misc.ModuleBrushListFilterable;
import cz.hartrik.sg2.app.module.misc.ModuleBrushThumbnails;
import cz.hartrik.sg2.app.module.misc.ModulePerformanceInfo;
import cz.hartrik.sg2.app.module.options.*;
import cz.hartrik.sg2.app.module.script.CreateScriptSubmodule;
import cz.hartrik.sg2.app.module.script.MenuModuleScriptFolder;
import cz.hartrik.sg2.app.module.script.ModuleToolBarScriptDialog;
import cz.hartrik.sg2.app.module.tools.PasteSaveSubmodule;
import cz.hartrik.sg2.app.module.tools.RandomizerSubmodule;
import cz.hartrik.sg2.app.module.tools.ShapesSubmodule;
import cz.hartrik.sg2.app.module.tools.TemplatesSubmodule;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.io.*;
import cz.hartrik.sg2.io.zip.resource.ResourceTypeManager;
import cz.hartrik.sg2.io.zip.ZipIO;
import cz.hartrik.sg2.io.zip.ZipIOBrushTemplate;
import cz.hartrik.sg2.io.zip.ZipIOSerial;
import cz.hartrik.sg2.world.BasicElement;
import cz.hartrik.sg2.world.ModularWorld;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Supplier;
import javafx.stage.Window;

/**
 * @version 2017-07-05
 * @author Patrik Harag
 */
public class MainModules {

    public static ApplicationModule[] modules() {

        // io
        Supplier<BrushManager> bmSupplier =
                () -> Main.getFrame().getFrameController().getBrushManager();

        ElementAreaProvider<ModularWorld> areaProvider =
                (w, h, size) -> new ModularWorld(w, h, size, BasicElement.AIR);

        ResourceTypeManager rtManager = new ResourceTypeManager(bmSupplier, 0);

        AppInfo appInfo = new AppInfo(Main.APP_NAME, Main.APP_VERSION);
        ZipIO<ModularWorld> serZipIO = new ZipIOSerial<>(appInfo, areaProvider, rtManager);
        ZipIO<ModularWorld> tmpZipIO = new ZipIOBrushTemplate<>(appInfo, areaProvider, rtManager);

        IOProvider<ModularWorld> ioProvider = new BasicIOProvider<>(Arrays.asList(serZipIO, tmpZipIO));

        JFXMultiFileChooser chooser = new JFXMultiFileChooser(ioProvider, Paths.get("."));
        UIProvider<Window> uiProvider = new JFXUIProvider<>(chooser);

        IOManager<ModularWorld, Window> ioManager = new IOManager<>(ioProvider, uiProvider);
        ioManager.setListener(Main::showFileName);

        // scripty

        final Path scripts = Paths.get("").resolve("scripts");

        // ...

        return new ApplicationModule[] {
            new ModulePerformanceInfo(),
            new ModuleBrushThumbnails(32, 32),
            new ModuleBrushListFilterable(),

            new MenuModule(Strings.get("module.io"))
                    .add(new FileSubmodule(ioManager))
                    .add(new CompoundMenuSubmodule(
                            new StatsSubmodule(),
                            new ScreenshotSubmodule())),

            new MenuModule(Strings.get("module.edit"))
                    .add(new SizeChangeSubmodule())
                    .add(new TransformSubmodule())
                    .add(new EditSubmodule())
                    .add(new PerformanceTestSubmodule()),

            new MenuModule(Strings.get("module.tools"))
                    .add(new TitleMenuSubmodule(Strings.get("module.tools.basic-tools")))
                    .add(new ShapesSubmodule())
                    .add(new RandomizerSubmodule())
                    .add(new TitleMenuSubmodule(Strings.get("module.tools.templates")))
                    .add(new PasteSaveSubmodule(ioManager))
                    .add(new TemplatesSubmodule()),

            new MenuModule(Strings.get("module.options"))
                    .add(new EngineSpeedSubmodule(EngineSpeedSubmodule.Settings.PROCESSOR))
                    .add(new RendererOptionsSubmodule())
                    .add(new NoBottomSubmodule()),

            new MenuModuleScriptFolder(Strings.get("module.script"), scripts)
                    .add(new CompoundMenuSubmodule(
                            new OpenPathSubmodule(scripts, Strings.get("module.script.open-folder")),
                            new CreateScriptSubmodule(scripts))),

            new MenuModule(Strings.get("module.about"))
                    .add(new AboutSubmodule()),

//            new MenuModule("* Test")
//                    .add(new _TestSubModule()),

            new ModuleToolBarProcessorSwitch(),
            new ModuleToolBarSizeChange(),
            new ModuleToolBarScale(),
            new ModuleToolBarSimpleButton(Strings.get("module.edit.fill"), EditServices.SERVICE_EDIT_FILL),
            new ModuleToolBarSimpleButton(Strings.get("module.io.scr"), ScreenshotService.SERVICE_SCREENSHOT),
            new ModuleToolBarScriptDialog(Strings.get("module.script.scripting-dialog")),

            new ModuleDragAndDropIO(ioManager),
        };
    }

}