package cz.hartrik.sg2.app.module.frame.module.io;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.frame.module.edit.EditServices;
import javafx.scene.control.MenuItem;

/**
 * @version 2014-12-02
 * @author Patrik Harag
 */
public class StatsSubmodule extends MenuSubmodule<Frame, FrameController> {

    public StatsSubmodule(boolean register) {
        super(register);
    }

    @Override
    public void register(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        new StatsService(stage, controller).register(manager);
    }

    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        MenuItem iStats = new MenuItem("Zobrazit statistiky");
        iStats.setOnAction((e) -> manager.run(EditServices.SERVICE_STATS));
        
        return new MenuItem[] { iStats };
    }
    
}
