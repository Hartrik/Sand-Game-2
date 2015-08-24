
package cz.hartrik.sg2.app.module.frame.module.edit;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.module.dialog.size.ChangeSizeDialog;
import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

/**
 * @version 2015-02-07
 * @author Patrik Harag
 */
public class SizeChangeSubmodule extends MenuSubmodule<Frame, FrameController> {

    public SizeChangeSubmodule(boolean register) {
        super(register);
    }

    @Override
    public void register(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        new SizeChangeService(stage, controller).register(manager);
    }

    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        MenuItem iChangeSize = new MenuItem("Změnit rozměry plátna");
        
        Image icon = Resources.image(ChangeSizeDialog.ICON, ChangeSizeDialog.class);
        iChangeSize.setGraphic(new ImageView(icon));
        iChangeSize.setOnAction((e) -> manager.run(EditServices.SERVICE_CHANGE_SIZE));
        iChangeSize.setAccelerator(KeyCombination.valueOf("shift+r"));
        
        return new MenuItem[] { iChangeSize };
    }
    
}