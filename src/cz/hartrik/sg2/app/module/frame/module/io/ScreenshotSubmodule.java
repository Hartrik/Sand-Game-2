
package cz.hartrik.sg2.app.module.frame.module.io;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * Modul přidá kromě vstupních a výstupních operací i položku pro vytvoření
 * screenshotu.
 * 
 * @version 2014-12-02
 * @author Patrik Harag
 */
public class ScreenshotSubmodule extends MenuSubmodule<Frame, FrameController> {

    public ScreenshotSubmodule(boolean register) {
        super(register);
    }

    @Override
    public void register(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        new ScreenshotService(stage, controller).register(manager);
    }
    
    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {
    
        MenuItem item = new MenuItem("Rychlý screenshot");
        item.setAccelerator(KeyCombination.valueOf("ctrl+p"));
        item.setOnAction(e -> manager.run(IOServices.SERVICE_SCREENSHOT));
        
        return new MenuItem[] { item };
    }
    
}