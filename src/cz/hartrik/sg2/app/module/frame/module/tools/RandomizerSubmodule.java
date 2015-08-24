package cz.hartrik.sg2.app.module.frame.module.tools;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.frame.module.script.ToolFactory;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.tool.Randomizer;
import cz.hartrik.sg2.tool.Shape;
import cz.hartrik.sg2.tool.Tool;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;

/**
 * @version 2015-03-29
 * @author Patrik Harag
 */
public class RandomizerSubmodule extends MenuSubmodule<Frame, FrameController> {

    private ChangeListener<Tool> listener;
    
    public RandomizerSubmodule() {
        super(false);
    }
    
    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        Controls controls = controller.getControls();
        ToolFactory factory = ToolFactory.getInstance();
        
        this.listener = (o, ov, selected) -> {
            if (selected instanceof Shape)
                controls.setPrimaryTool(factory.randomizer((Shape) selected));
        };
        
        CheckMenuItem item = new CheckMenuItem("Povolit randomizaci");
        item.setSelected(false);
        item.selectedProperty().addListener((o, ov, selected) -> {
            if (ov == selected) return;

            if (selected) {
                listener.changed(null, null, controls.getPrimaryTool());
                controls.primaryToolProperty().addListener(listener);
            } else {
                controls.primaryToolProperty().removeListener(listener);
                
                Tool primary = controls.getPrimaryTool();
                if (primary instanceof Randomizer)
                    controls.setPrimaryTool(((Randomizer) primary).getShape());
            }
        });
        
        return new MenuItem[] { item };
    }
    
}