
package cz.hartrik.sg2.app.module.dialog.size;

import cz.hartrik.common.Pair;
import cz.hartrik.common.io.Resources;
import cz.hartrik.common.ui.javafx.FXMLControlledStage;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * Dialog pro výběr rozměrů plátna.
 *
 * @version 2016-06-21
 * @author Patrik Harag
 */
public class ChangeSizeDialog extends FXMLControlledStage<ChangeSizeController> {

    public static final String ICON = "icon - resize.png";

    public ChangeSizeDialog(Window owner,
            int initalWidth, int initalHeight, int initialChunkSize) {

        super(ChangeSizeDialog.class.getResource("ChangeSize.fxml"));

        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Rozměry plátna");
        getIcons().setAll(Resources.image(ICON, getClass()));

        setMinWidth(540); setMinHeight(400);
        setMaxWidth(700); setMaxHeight(500);

        controller.setInital(initalWidth, initalHeight, initialChunkSize);
        controller.reset();
    }

    public Pair<Integer, Integer> getSize() {
        return controller.getSize();
    }

    public int getChunkSize() {
        return controller.getChunkSize();
    }

}