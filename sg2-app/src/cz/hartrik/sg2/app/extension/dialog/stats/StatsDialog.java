
package cz.hartrik.sg2.app.extension.dialog.stats;

import cz.hartrik.common.io.Resources;
import cz.hartrik.common.ui.javafx.FXMLControlledStage;
import cz.hartrik.sg2.world.ElementArea;
import java.net.URL;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * Dialog zobrazující statistiky o plátně.
 * 
 * @version 2014-11-29
 * @author Patrik Harag
 */
public class StatsDialog extends FXMLControlledStage<StatsController> {
    
    public static final String ICON = "icon - stats.png";
    private static final URL URL = StatsDialog.class.getResource("Stats.fxml");
    
    public StatsDialog(Window owner, ElementArea area) {
        super(URL);

        setTitle("Statistiky");
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        getIcons().setAll(Resources.image(ICON, getClass()));
        
        setMinWidth(600);
        setMinHeight(350);
        
        controller.update(area);
    }
    
    public void update(ElementArea area) {
        controller.update(area);
    }
    
}