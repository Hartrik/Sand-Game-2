
package cz.hartrik.sg2.app.module.dialog.size;

import cz.hartrik.common.Pair;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/**
 * Controller ovládající panel s nastevením rozměrů plátna.
 * 
 * @version 2014-03-31
 * @author Patrik Harag
 */
public class ChangeSizeController implements Initializable {
    
    private int initalWidth, initalHeight;
    private boolean changed = false;
    
    @FXML private Slider sliderWidth;
    @FXML private Slider sliderHeight;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    
    public void setInital(int initalWidth, int initalHeight) {
        this.initalWidth  = initalWidth;
        this.initalHeight = initalHeight;
    }
            
    @FXML
    public void reset() {
        sliderWidth.setValue(initalWidth);
        sliderHeight.setValue(initalHeight);
    }
    
    @FXML
    public void close() {
        ((Stage) sliderWidth.getScene().getWindow()).close();
    }
    
    @FXML
    public void changeAndClose() {
        changed = true;
        close();
    }
    
    public Pair<Integer, Integer> getSize() {
        return changed
                ? Pair.of(
                    (int) sliderWidth.getValue(),
                    (int) sliderHeight.getValue())
                : null;
    }
    
}