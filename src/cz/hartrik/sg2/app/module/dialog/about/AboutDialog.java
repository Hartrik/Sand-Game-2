
package cz.hartrik.sg2.app.module.dialog.about;

import cz.hartrik.common.io.Resources;
import cz.hartrik.common.ui.javafx.FXMLControlledStage;
import java.net.URL;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * Dialog o programu.
 * 
 * @version 2015-04-04
 * @author Patrik Harag
 */
public class AboutDialog extends FXMLControlledStage<AboutController> {
    
    private static final URL URL = AboutDialog.class.getResource("About.fxml");
    public static final String ICON = "icon - info.png";
    
    public AboutDialog(Window owner) {
        super(URL);

        setTitle("O programu");
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        getIcons().setAll(Resources.image(ICON, getClass()));
        
        setMinWidth(550);
        setMinHeight(400);
    }
    
}