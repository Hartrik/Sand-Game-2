
package cz.hartrik.sg2.app.sandbox;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.StageModule;
import cz.hartrik.sg2.app.module.frame.module.CompoundMenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.MenuModule;
import cz.hartrik.sg2.app.module.frame.module.ModuleToolBarSimpleButton;
import cz.hartrik.sg2.app.module.frame.module.TitleSubmodule;
import cz.hartrik.sg2.app.module.frame.module.about.*;
import cz.hartrik.sg2.app.module.frame.module.edit.*;
import cz.hartrik.sg2.app.module.frame.module.io.*;
import cz.hartrik.sg2.app.module.frame.module.misc.*;
import cz.hartrik.sg2.app.module.frame.module.options.*;
import cz.hartrik.sg2.app.module.frame.module.script.*;
import cz.hartrik.sg2.app.module.frame.module.tools.*;
import cz.hartrik.sg2.app.module.io.*;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.world.BasicElement;
import cz.hartrik.sg2.world.ModularWorld;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Supplier;
import javafx.stage.Window;

/**
 * @version 2016-06-21
 * @author Patrik Harag
 */
public class MainModules {

    @SuppressWarnings({"unchecked"})
    public static StageModule<Frame, FrameController>[] modules() {

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

        return new StageModule[] {
            new ModulePerformanceInfo(),
            new ModuleBrushThumbnails(32, 32),
            new ModuleBrushListFilterable(),

            new MenuModule<Frame, FrameController>("Soubor")
                    .add(new FileSubmodule(true, ioManager))
                    .add(new CompoundMenuSubmodule<>(
                            new StatsSubmodule(true),
                            new ScreenshotSubmodule(true))),

            new MenuModule<Frame, FrameController>("Úpravy")
                    .add(new SizeChangeSubmodule(true))
                    .add(new TransformSubmodule(true))
                    .add(new InsertSubmodule(true))
                    .add(new PerformanceTestSubmodule(true)),

            new MenuModule<Frame, FrameController>("Nástroje")
                    .add(new TitleSubmodule("Základní tvary"))
                    .add(new ShapesSubmodule())
                    .add(new RandomizerSubmodule())
                    .add(new TitleSubmodule("Šablony"))
                    .add(new PasteSaveSubmodule(ioProvider, uiProvider))
                    .add(new TemplatesSubmodule()),

            new MenuModule<Frame, FrameController>("Možnosti")
                    .add(new EngineSpeedSubmodule(EngineSpeedSubmodule.Settings.BOTH))
                    .add(new RendererOptionsSubmodule())
                    .add(new NoBottomSubmodule()),

            new MenuModuleScriptFolder("Scripty", scripts)
                    .add(new CompoundMenuSubmodule<>(
                            new OpenPathSubmodule(scripts, "Otevřít složku se scripty"),
                            new CreateScriptSubmodule(scripts))),

            new MenuModule<Frame, FrameController>("Informace")
                    .add(new AboutSubmodule(true)),

//            new MenuModule<Frame, FrameController>("* Test")
//                    .add(new _TestSubModule()),

            new ModuleToolBarProcessorSwitch(true),
            new ModuleToolBarSizeChange(false),
            new ModuleToolBarScale("Přiblížení:"),
            new ModuleToolBarSimpleButton("Naplnit", EditServices.SERVICE_EDIT_FILL),
            new ModuleToolBarSimpleButton("Screenshot", IOServices.SERVICE_SCREENSHOT),
            new ModuleToolBarScriptDialog("Script"),

            new ModuleDragAndDropIO(ioManager),
        };
    }

}