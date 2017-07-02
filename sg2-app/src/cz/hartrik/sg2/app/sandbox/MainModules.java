
package cz.hartrik.sg2.app.sandbox;

import cz.hartrik.sg2.app.module.tools.PasteSaveSubmodule;
import cz.hartrik.sg2.app.module.tools.RandomizerSubmodule;
import cz.hartrik.sg2.app.module.tools.ShapesSubmodule;
import cz.hartrik.sg2.app.module.tools.TemplatesSubmodule;
import cz.hartrik.sg2.app.module.script.MenuModuleScriptFolder;
import cz.hartrik.sg2.app.module.script.ModuleToolBarScriptDialog;
import cz.hartrik.sg2.app.module.script.CreateScriptSubmodule;
import cz.hartrik.sg2.app.module.misc.ModuleBrushThumbnails;
import cz.hartrik.sg2.app.module.misc.ModuleBrushListFilterable;
import cz.hartrik.sg2.app.module.misc.ModulePerformanceInfo;
import cz.hartrik.sg2.app.module.edit.TransformSubmodule;
import cz.hartrik.sg2.app.module.edit.SizeChangeSubmodule;
import cz.hartrik.sg2.app.module.edit.EditServices;
import cz.hartrik.sg2.app.module.edit.PerformanceTestSubmodule;
import cz.hartrik.sg2.app.module.edit.ModuleToolBarSizeChange;
import cz.hartrik.sg2.app.module.edit.EditSubmodule;
import cz.hartrik.sg2.app.module.options.EngineSpeedSubmodule;
import cz.hartrik.sg2.app.module.options.ModuleToolBarScale;
import cz.hartrik.sg2.app.module.options.ModuleToolBarProcessorSwitch;
import cz.hartrik.sg2.app.module.options.RendererOptionsSubmodule;
import cz.hartrik.sg2.app.module.options.NoBottomSubmodule;
import cz.hartrik.sg2.app.module.io.FileSubmodule;
import cz.hartrik.sg2.app.module.io.ModuleDragAndDropIO;
import cz.hartrik.sg2.app.module.io.OpenPathSubmodule;
import cz.hartrik.sg2.app.module.io.ScreenshotSubmodule;
import cz.hartrik.sg2.app.module.io.StatsSubmodule;
import cz.hartrik.sg2.app.module.io.ScreenshotService;
import cz.hartrik.sg2.app.module.about.AboutSubmodule;
import cz.hartrik.sg2.app.extension.io.JFXUIProvider;
import cz.hartrik.sg2.app.extension.io.BasicIOProvider;
import cz.hartrik.sg2.app.extension.io.JFXMultiFileChooser;
import cz.hartrik.sg2.app.extension.io.IOManager;
import cz.hartrik.sg2.app.extension.io.ResourceTypeManager;
import cz.hartrik.sg2.app.extension.io.IOProvider;
import cz.hartrik.sg2.app.extension.io.ZipIOBrushTemplate;
import cz.hartrik.sg2.app.extension.io.ElementAreaProvider;
import cz.hartrik.sg2.app.extension.io.ZipIO;
import cz.hartrik.sg2.app.extension.io.UIProvider;
import cz.hartrik.sg2.app.extension.io.ZipIOSerial;
import cz.hartrik.sg2.app.module.ApplicationModule;
import cz.hartrik.sg2.app.module.CompoundMenuSubmodule;
import cz.hartrik.sg2.app.module.MenuModule;
import cz.hartrik.sg2.app.module.ModuleToolBarSimpleButton;
import cz.hartrik.sg2.app.module.TitleMenuSubmodule;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.world.BasicElement;
import cz.hartrik.sg2.world.ModularWorld;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Supplier;
import javafx.stage.Window;

/**
 * @version 2016-07-10
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

        ZipIO<ModularWorld> serZipIO = new ZipIOSerial<>(areaProvider, rtManager);
        ZipIO<ModularWorld> tmpZipIO = new ZipIOBrushTemplate<>(areaProvider, rtManager);

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

            new MenuModule("Soubor")
                    .add(new FileSubmodule(ioManager))
                    .add(new CompoundMenuSubmodule(
                            new StatsSubmodule(),
                            new ScreenshotSubmodule())),

            new MenuModule("Úpravy")
                    .add(new SizeChangeSubmodule())
                    .add(new TransformSubmodule())
                    .add(new EditSubmodule())
                    .add(new PerformanceTestSubmodule()),

            new MenuModule("Nástroje")
                    .add(new TitleMenuSubmodule("Základní tvary"))
                    .add(new ShapesSubmodule())
                    .add(new RandomizerSubmodule())
                    .add(new TitleMenuSubmodule("Šablony"))
                    .add(new PasteSaveSubmodule(ioManager))
                    .add(new TemplatesSubmodule()),

            new MenuModule("Možnosti")
                    .add(new EngineSpeedSubmodule(EngineSpeedSubmodule.Settings.PROCESSOR))
                    .add(new RendererOptionsSubmodule())
                    .add(new NoBottomSubmodule()),

            new MenuModuleScriptFolder("Scripty", scripts)
                    .add(new CompoundMenuSubmodule(
                            new OpenPathSubmodule(scripts, "Otevřít složku se scripty"),
                            new CreateScriptSubmodule(scripts))),

            new MenuModule("Informace")
                    .add(new AboutSubmodule()),

//            new MenuModule("* Test")
//                    .add(new _TestSubModule()),

            new ModuleToolBarProcessorSwitch(),
            new ModuleToolBarSizeChange(),
            new ModuleToolBarScale("Přiblížení:"),
            new ModuleToolBarSimpleButton("Naplnit", EditServices.SERVICE_EDIT_FILL),
            new ModuleToolBarSimpleButton("Screenshot", ScreenshotService.SERVICE_SCREENSHOT),
            new ModuleToolBarScriptDialog("Script"),

            new ModuleDragAndDropIO(ioManager),
        };
    }

}