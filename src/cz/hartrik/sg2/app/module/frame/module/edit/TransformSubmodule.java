
package cz.hartrik.sg2.app.module.frame.module.edit;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

import static cz.hartrik.sg2.app.module.frame.module.edit.EditServices.*;

/**
 * Zaregistruje služby a vytvoří pro každou z nich položky do menu.
 * 
 * @version 2015-02-07
 * @author Patrik Harag
 */
public class TransformSubmodule extends MenuSubmodule<Frame, FrameController> {

    public TransformSubmodule(boolean register) {
        super(register);
    }

    @Override
    public void register(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        new TransformServices(controller).register(manager);
    }

    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        MenuItem iFlipV = new MenuItem("Překlopit svisle");
        iFlipV.setGraphic(img("icon - flip vertical.png"));
        iFlipV.setOnAction((e) -> manager.run(SERVICE_EDIT_FLIP_V));
        iFlipV.setAccelerator(KeyCombination.valueOf("shift+down"));
        
        MenuItem iFlipH = new MenuItem("Překlopit vodorovně");
        iFlipH.setGraphic(img("icon - flip horizontal.png"));
        iFlipH.setOnAction((e) -> manager.run(SERVICE_EDIT_FLIP_H));
        iFlipH.setAccelerator(KeyCombination.valueOf("shift+up"));
        
        MenuItem iRotR = new MenuItem("Otočit doprava o 90°");
        iRotR.setGraphic(img("icon - redo.png"));
        iRotR.setOnAction((e) -> manager.run(SERVICE_EDIT_ROTATE_R));
        iRotR.setAccelerator(KeyCombination.valueOf("shift+right"));
        
        MenuItem iRotL = new MenuItem("Otočit doleva o 90°");
        iRotL.setGraphic(img("icon - undo.png"));
        iRotL.setOnAction((e) -> manager.run(SERVICE_EDIT_ROTATE_L));
        iRotL.setAccelerator(KeyCombination.valueOf("shift+left"));
        
        return new MenuItem[] { iFlipV, iFlipH, iRotR, iRotL };
    }
    
    private ImageView img(String fileName) {
        return new ImageView(Resources.image(fileName, getClass()));
    }
    
}