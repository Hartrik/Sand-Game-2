package cz.hartrik.sg2.app.module.frame.module.options;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.frame.module.ToolBarModule;
import javafx.scene.control.Button;

/**
 *
 * @version 2014-12-02
 * @author Patrik Harag
 */
public class ModuleToolBarProcessorSwitch
        extends ToolBarModule<Frame, FrameController> {

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
        
        Button button = new Button("Start/stop");
        button.setOnAction((e) -> {
            manager.run(OptionsServices.SERVICE_PROCESSOR_SWITCH);
            
            // pokud by došlo k nějaké chybě... renderer by měl běžet stále
            manager.run(OptionsServices.SERVICE_RENDERER_START);
        });
        
        controller.getToolBar().getItems().add(button);
    }
    
}