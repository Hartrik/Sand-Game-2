
package cz.hartrik.sg2.app.extension.dialog.size;

import cz.hartrik.common.Pair;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/**
 * Controller ovládající panel s nastevením rozměrů plátna.
 *
 * @version 2017-07-11
 * @author Patrik Harag
 */
public class ChangeSizeController implements Initializable {

    private static final int MAX_SIZE = 1500;

    private int initialWidth, initialHeight, initialChunkSize;
    private int lastWidth, lastHeight;
    private boolean changed = false;

    @FXML private Slider sliderChunkSize;
    @FXML private Slider sliderWidth;
    @FXML private Slider sliderHeight;

    @FXML private Label labelSize;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sliderChunkSize.valueProperty().addListener((ov, o, n) -> {
            int chunkSize = n.intValue();

            int max = MAX_SIZE - (MAX_SIZE % chunkSize);
            sliderWidth.setMax(max);
            sliderHeight.setMax(max);

            sliderWidth.setMin(chunkSize);
            sliderHeight.setMin(chunkSize);

            sliderWidth.setBlockIncrement(chunkSize);
            sliderHeight.setBlockIncrement(chunkSize);
            sliderWidth.setMajorTickUnit(chunkSize);
            sliderHeight.setMajorTickUnit(chunkSize);

            int tmpWidth = lastWidth;
            int tmpHeight = lastHeight;
            sliderWidth.setValue(lastWidth / chunkSize * chunkSize);
            sliderHeight.setValue(lastHeight / chunkSize * chunkSize);
            lastWidth = tmpWidth;
            lastHeight = tmpHeight;
        });

        sliderWidth.valueProperty().addListener((ov, o, n) -> {
            lastWidth = n.intValue();
            updateSize();
        });

        sliderHeight.valueProperty().addListener((ov, o, n) -> {
            lastHeight = n.intValue();
            updateSize();
        });
    }

    private void updateSize() {
        labelSize.setText(String.format("%d × %d",
                (int) sliderWidth.getValue(), (int) sliderHeight.getValue()));
    }

    public void setInitial(int width, int height, int chunkSize) {
        this.initialWidth = this.lastWidth = width;
        this.initialHeight = this.lastHeight = height;
        this.initialChunkSize = chunkSize;
    }

    @FXML
    public void reset() {
        sliderWidth.setValue(initialWidth);
        sliderHeight.setValue(initialHeight);
        sliderChunkSize.setValue(initialChunkSize);
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

    public int getChunkSize() {
        return (int) sliderChunkSize.getValue();
    }

}