
package cz.hartrik.sg2.app.module.edit;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.app.service.Require;
import cz.hartrik.sg2.app.service.ServiceManager;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

import static cz.hartrik.sg2.app.module.edit.EditServices.*;

/**
 * Sub-modul do menu, přidávající základní editační nástroje.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
@Require(EditServices.class)
public class EditSubmodule implements MenuSubmodule {

    @Override
    public MenuItem[] createMenuItems(Application app) {
        ServiceManager manager = app.getServiceManager();

        MenuItem iClear = new MenuItem(Strings.get("module.edit.clear"));
        iClear.setGraphic(img("icon - clear.png"));
        iClear.setOnAction((e) -> manager.run(SERVICE_EDIT_CLEAR));
        iClear.setAccelerator(KeyCombination.valueOf("shift+c"));

        MenuItem iFill = new MenuItem(Strings.get("module.edit.fill"));
        iFill.setGraphic(img("icon - fill.png"));
        iFill.setOnAction((e) -> manager.run(SERVICE_EDIT_FILL));
        iFill.setAccelerator(KeyCombination.valueOf("shift+f"));

        MenuItem iBound = new MenuItem(Strings.get("module.edit.border"));
        iBound.setGraphic(img("icon - border - canvas.png"));
        iBound.setOnAction((e) -> manager.run(SERVICE_EDIT_BOUNDS));
        iBound.setAccelerator(KeyCombination.valueOf("shift+b"));

        MenuItem iBoundBH = new MenuItem(Strings.get("module.edit.border-bh"));
        iBoundBH.setGraphic(img("icon - border - canvas.png"));
        iBoundBH.setOnAction((e) -> manager.run(SERVICE_EDIT_BOUNDS_BH));
        iBoundBH.setAccelerator(KeyCombination.valueOf("shift+d"));

        MenuItem iBoundT = new MenuItem(Strings.get("module.edit.border-top"));
        iBoundT.setGraphic(img("icon - border - top.png"));
        iBoundT.setOnAction((e) -> manager.run(SERVICE_EDIT_BOUNDS_TOP));
        iBoundT.setAccelerator(KeyCombination.valueOf("shift+u"));

        MenuItem iBoundB = new MenuItem(Strings.get("module.edit.border-bottom"));
        iBoundB.setGraphic(img("icon - border - bottom.png"));
        iBoundB.setOnAction((e) -> manager.run(SERVICE_EDIT_BOUNDS_BOTTOM));
        iBoundB.setAccelerator(KeyCombination.valueOf("shift+l"));

        MenuItem IBoundCC = new MenuItem(Strings.get("module.edit.border-chunks"));
        IBoundCC.setGraphic(img("icon - border - chunk.png"));
        IBoundCC.setOnAction((e) -> manager.run(SERVICE_EDIT_BOUNDS_CHUNK));

        return new MenuItem[] {
            iClear, iFill, iBoundBH, iBound, iBoundT, iBoundB, IBoundCC
        };
    }

    private ImageView img(String fileName) {
        return new ImageView(Resources.image(fileName, getClass()));
    }

}