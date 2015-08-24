
package cz.hartrik.sg2.app.module.frame.module.edit;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import javafx.scene.control.MenuItem;

import static cz.hartrik.sg2.app.module.frame.module.edit.EditServices.*;

/**
 * @version 2014-12-02
 * @author Patrik Harag
 */
public class PerformanceTestSubmodule extends MenuSubmodule<Frame, FrameController> {

    public PerformanceTestSubmodule(boolean register) {
        super(register);
    }

    @Override
    public void register(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        new PerformanceTestServices(controller).register(manager);
    }

    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        MenuItem iSandTest = new MenuItem("Test výkonu - písek");
        iSandTest.setOnAction((e) -> manager.run(SERVICE_TEST_SAND_FALL));
        
        return new MenuItem[] { iSandTest };
    }
    
}