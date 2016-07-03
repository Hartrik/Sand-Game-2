
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
 * @version 2016-07-03
 * @author Patrik Harag
 */
public class InsertSubmodule extends MenuSubmodule<Frame, FrameController> {

    public InsertSubmodule(boolean register) {
        super(register);
    }

    @Override
    public void register(Frame stage, FrameController controller,
            ServiceManager manager) {

        new InsertServices(controller).register(manager);
    }

    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {

        MenuItem iClear = new MenuItem("Vyčistit");
        iClear.setGraphic(img("icon - clear.png"));
        iClear.setOnAction((e) -> manager.run(SERVICE_EDIT_CLEAR));
        iClear.setAccelerator(KeyCombination.valueOf("shift+c"));

        MenuItem iFill = new MenuItem("Naplnit");
        iFill.setGraphic(img("icon - fill.png"));
        iFill.setOnAction((e) -> manager.run(SERVICE_EDIT_FILL));
        iFill.setAccelerator(KeyCombination.valueOf("shift+f"));

        MenuItem iBound = new MenuItem("Ohraničit plátno (vybraným štětcem)");
        iBound.setGraphic(img("icon - border - canvas.png"));
        iBound.setOnAction((e) -> manager.run(SERVICE_EDIT_BOUNDS));
        iBound.setAccelerator(KeyCombination.valueOf("shift+b"));

        MenuItem iBoundBH = new MenuItem("Ohraničit plátno");
        iBoundBH.setGraphic(img("icon - border - canvas.png"));
        iBoundBH.setOnAction((e) -> manager.run(SERVICE_EDIT_BOUNDS_BH));
        iBoundBH.setAccelerator(KeyCombination.valueOf("shift+d"));

        MenuItem iBoundT = new MenuItem("Ohraničit horní okraj (vybraným štětcem)");
        iBoundT.setGraphic(img("icon - border - top.png"));
        iBoundT.setOnAction((e) -> manager.run(SERVICE_EDIT_BOUNDS_TOP));
        iBoundT.setAccelerator(KeyCombination.valueOf("shift+u"));

        MenuItem iBoundB = new MenuItem("Ohraničit dolní okraj (vybraným štětcem)");
        iBoundB.setGraphic(img("icon - border - bottom.png"));
        iBoundB.setOnAction((e) -> manager.run(SERVICE_EDIT_BOUNDS_BOTTOM));
        iBoundB.setAccelerator(KeyCombination.valueOf("shift+l"));

        MenuItem IBoundCC = new MenuItem("Ohraničit chunky (vybraným štětcen)");
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
