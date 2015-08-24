package cz.hartrik.sg2.app.module.frame.module;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import javafx.scene.control.Button;

/**
 * @version 2014-12-22
 * @author Patrik Harag
 */
public class ModuleToolBarSimpleButton extends ToolBarModule<Frame, FrameController> {

    protected final String text;
    private final String[] services;
    
    public ModuleToolBarSimpleButton(String text, String... services) {
        super(false);
        this.text = text;
        this.services = services;
    }

    @Override
    public void register(Frame stage, FrameController controller,
            ServiceManager manager) {
    }

    @Override
    public void setUp(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        Button button = new Button(text);
        button.setOnAction((e) -> {
            for (String service : services)
                manager.run(service);
        });
        
        controller.getToolBar().getItems().add(button);
    }
    
}