
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
 * @version 2015-02-07
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

        MenuItem iBoundB = new MenuItem("Ohraničit plátno");
        iBoundB.setGraphic(img("icon - border - canvas.png"));
        iBoundB.setOnAction((e) -> manager.run(SERVICE_EDIT_BOUNDS_BH));

        MenuItem iBoundBC = new MenuItem("Ohraničit plátno (vlastním el.)");
        iBoundBC.setGraphic(img("icon - border - canvas.png"));
        iBoundBC.setOnAction((e) -> manager.run(SERVICE_EDIT_BOUNDS));

        MenuItem iBoundC = new MenuItem("Ohraničit chunky");
        iBoundC.setGraphic(img("icon - border - chunk.png"));
        iBoundC.setOnAction((e) -> manager.run(SERVICE_EDIT_BOUNDS_CHUNK_W));

        MenuItem IBoundCC = new MenuItem("Ohraničit chunky (vlastním el.)");
        IBoundCC.setGraphic(img("icon - border - chunk.png"));
        IBoundCC.setOnAction((e) -> manager.run(SERVICE_EDIT_BOUNDS_CHUNK));

        return new MenuItem[] {
            iClear, iFill, iBoundB, iBoundBC, iBoundC, IBoundCC
        };
    }

    private ImageView img(String fileName) {
        return new ImageView(Resources.image(fileName, getClass()));
    }

}
