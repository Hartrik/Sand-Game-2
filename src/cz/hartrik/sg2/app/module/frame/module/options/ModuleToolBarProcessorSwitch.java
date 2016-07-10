package cz.hartrik.sg2.app.module.frame.module.options;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.module.frame.Application;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.ProcessingState;
import cz.hartrik.sg2.app.module.frame.module.ApplicationModule;
import cz.hartrik.sg2.app.module.frame.service.Require;
import cz.hartrik.sg2.app.module.frame.service.ServiceManager;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Modul, který do toolbaru přidá start/stop tlačítko.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@Require(EnginePrimitivesServices.class)
public class ModuleToolBarProcessorSwitch implements ApplicationModule {

    private final Image ICON_PAUSE = Resources.image("icon - pause.png", getClass());
    private final Image ICON_START = Resources.image("icon - start.png", getClass());

    @Override
    public void init(Application app) {
        FrameController controller = app.getController();
        ServiceManager manager = app.getServiceManager();

        Button button = new Button();
        button.setPrefWidth(50);

        button.setOnAction((e) -> {
            manager.run(EnginePrimitivesServices.SERVICE_PROCESSOR_SWITCH);

            // pokud by došlo k nějaké chybě... renderer by měl jinak běžet
            manager.run(EnginePrimitivesServices.SERVICE_RENDERER_START);
        });

        controller.addOnEngineStateChanged((state) -> {
            Image icon = (state == ProcessingState.RUNNING)
                    ? ICON_PAUSE
                    : ICON_START;

            button.setGraphic(new ImageView(icon));
        });

        controller.getToolBar().getItems().add(button);
    }

}