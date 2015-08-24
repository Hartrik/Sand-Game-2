package cz.hartrik.sg2.app.module.frame.module.options;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.world.module.ModuleNoBottom;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;

/**
 *
 * @version 2014-12-02
 * @author Patrik Harag
 */
public class NoBottomSubmodule extends MenuSubmodule<Frame, FrameController> {
    
    public NoBottomSubmodule() {
        super(true);
    }

    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {

        final ModuleNoBottom module = new ModuleNoBottom();
        
        CheckMenuItem item = new CheckMenuItem("Propadávání elementů dnem plátna");
        item.selectedProperty().addListener((o, ov, selected) -> {
            
            controller.removeWorldModule(module);
            // pro jistotu u obou případů (aby tam nebyly dva)
            
            if (selected)
                controller.addWorldModule(module);
        });
        
        return new MenuItem[] { item };
    }

}