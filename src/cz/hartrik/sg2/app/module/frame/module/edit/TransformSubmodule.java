
package cz.hartrik.sg2.app.module.frame.module.edit;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.module.frame.Application;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.service.Require;
import cz.hartrik.sg2.app.module.frame.service.ServiceManager;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

import static cz.hartrik.sg2.app.module.frame.module.edit.TransformServices.*;

/**
 * Sub-modul do menu, přidává nástroje pro transformaci.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
@Require(TransformServices.class)
public class TransformSubmodule implements MenuSubmodule {

    @Override
    public MenuItem[] createMenuItems(Application app) {
        ServiceManager manager = app.getServiceManager();

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