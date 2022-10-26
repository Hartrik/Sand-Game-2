
package cz.hartrik.common.ui.javafx;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * {@link Stage} s obsahem načteným ze souboru FXML.
 * 
 * @version 2015-04-04
 * @author Patrik Harag
 */
public class FXMLStage extends Stage {

    public FXMLStage(URL url) {
        this(url, null);
    }
    
    public FXMLStage(URL url, ResourceBundle resourceBundle) {
        try {
            setScene(new Scene(resourceBundle == null
                    ? FXMLLoader.load(url)
                    : FXMLLoader.load(url, resourceBundle)));
            
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
    
}