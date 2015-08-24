package cz.hartrik.sg2.app.module.frame.module.edit;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.frame.module.ToolBarModule;
import javafx.scene.control.Button;

/**
 * @version 2014-12-02
 * @author Patrik Harag
 */
public class ModuleToolBarSizeChange
        extends ToolBarModule<Frame, FrameController> {

    public ModuleToolBarSizeChange(boolean register) {
        super(register);
    }

    @Override
    public void register(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        new SizeChangeService(stage, controller).register(manager);
    }

    @Override
    public void setUp(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        Button button = new Button("Změnit rozměry");
        button.setOnAction((e) -> manager.run(EditServices.SERVICE_CHANGE_SIZE));
        
        controller.getToolBar().getItems().add(button);
    }

}