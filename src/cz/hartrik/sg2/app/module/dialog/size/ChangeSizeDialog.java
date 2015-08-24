
package cz.hartrik.sg2.app.module.dialog.size;

import cz.hartrik.common.Pair;
import cz.hartrik.common.io.Resources;
import cz.hartrik.common.ui.javafx.FXMLControlledStage;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * Dialog pro výběr rozměrů plátna.
 * 
 * @version 2015-04-04
 * @author Patrik Harag
 */
public class ChangeSizeDialog extends FXMLControlledStage<ChangeSizeController> {
    
    public static final String ICON = "icon - resize.png";
    
    public ChangeSizeDialog(Window owner, int initalWidth, int initalHeight) {
        super(ChangeSizeDialog.class.getResource("ChangeSize.fxml"));

        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Rozměry plátna");
        getIcons().setAll(Resources.image(ICON, getClass()));
        
        setMinWidth(350); setMinHeight(270);
        setMaxWidth(600); setMaxHeight(350);
        
        controller.setInital(initalWidth, initalHeight);
        controller.reset();
    }
    
    public Pair<Integer, Integer> getSize() {
        return controller.getSize();
    }
    
}