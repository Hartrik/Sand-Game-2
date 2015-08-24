package cz.hartrik.sg2.app.module.frame.module._test;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.tool.TurtleImpl;
import cz.hartrik.sg2.tool.CenteredRectangle;
import javafx.scene.control.MenuItem;

/**
 * @author Patrik Harag
 */
public class _TestSubModule extends MenuSubmodule<Frame, FrameController> {
    
    public _TestSubModule() {
        super(true);
    }

    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        MenuItem menuItem = new MenuItem("turtle test 1");
        menuItem.setOnAction(event -> {
            TurtleImpl turtle = new TurtleImpl(
                    controller.getWorld(),
                    controller.getControls().getPrimaryBrush(),
                    new CenteredRectangle(1, 1));
            
            int cycles = 180;
            
            turtle
                .repeat(cycles)
                    .center()
                    .repeat(100)
                        .move(3)
                        .draw(3)
                    .end()
                    .left(360/cycles)
                .end();
        });
        
        return new MenuItem[] { menuItem };
    }

    
//        MenuItem menuItem = new MenuItem("Test");
//        menuItem.setOnAction(event -> {
//            
//        });
//        
//        return new MenuItem[] { menuItem };
    
}