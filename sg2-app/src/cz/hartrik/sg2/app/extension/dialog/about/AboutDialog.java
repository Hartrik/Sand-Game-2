
package cz.hartrik.sg2.app.extension.dialog.about;

import cz.hartrik.common.io.LazyResources;
import cz.hartrik.common.ui.javafx.FXMLControlledStage;
import cz.hartrik.sg2.app.Strings;
import java.net.URL;
import java.util.function.Supplier;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * Dialog o programu.
 *
 * @version 2017-07-05
 * @author Patrik Harag
 */
public class AboutDialog extends FXMLControlledStage<AboutController> {

    private static final URL URL = AboutDialog.class.getResource("About.fxml");
    private static final String ICON_NAME = "icon - info.png";

    public static final Supplier<Image> ICON = LazyResources.image(
            ICON_NAME, AboutDialog.class);

    public AboutDialog(Window owner) {
        super(URL, Strings.getResourceBundle());

        setTitle(Strings.get("extension.about.title"));
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        getIcons().setAll(ICON.get());

        setMinWidth(550);
        setMinHeight(400);
    }

}