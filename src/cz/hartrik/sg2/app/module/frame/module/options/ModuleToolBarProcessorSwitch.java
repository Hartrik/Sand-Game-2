package cz.hartrik.sg2.app.module.frame.module.options;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.ProcessingState;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.frame.module.ToolBarModule;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @version 2016-06-26
 * @author Patrik Harag
 */
public class ModuleToolBarProcessorSwitch
        extends ToolBarModule<Frame, FrameController> {

    private final Image ICON_PAUSE = Resources.image("icon - pause.png", getClass());
    private final Image ICON_START = Resources.image("icon - start.png", getClass());

    public ModuleToolBarProcessorSwitch(boolean register) {
        super(register);
    }

    @Override
    public void register(Frame stage, FrameController controller,
            ServiceManager manager) {

        new EnginePrimitivesServices(controller).register(manager);
    }

    @Override
    public void setUp(Frame stage, FrameController controller,
            ServiceManager manager) {

        Button button = new Button();
        button.setPrefWidth(50);

        button.setOnAction((e) -> {
            manager.run(OptionsServices.SERVICE_PROCESSOR_SWITCH);

            // pokud by došlo k nějaké chybě... renderer by měl jinak běžet
            manager.run(OptionsServices.SERVICE_RENDERER_START);
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