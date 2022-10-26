
package cz.hartrik.common.ui.javafx;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * {@link Stage} s obsahem načteným ze souboru FXML. Na rozdíl od
 * {@link FXMLStage} má k dispozici kontroler.
 * 
 * @version 2015-04-04
 * @author Patrik Harag
 * @param <T> kontroler
 */
public class FXMLControlledStage<T extends Initializable> extends Stage {

    protected final T controller;
    
    // --- konstruktory
    
    public FXMLControlledStage(URL url) {
        this(url, null);
    }
    
    public FXMLControlledStage(URL url, ResourceBundle bundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(url, bundle);
        
        try {
            setScene(new Scene(fxmlLoader.load()));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        
        controller = fxmlLoader.getController();
    }

    // --- metody
    
    protected T getController() {
        return controller;
    }
    
}