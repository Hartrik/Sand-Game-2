package cz.hartrik.sg2.engine;

import cz.hartrik.common.Color;
import javafx.scene.image.*;

/**
 * @author Patrik Harag
 * @version 2017-08-06
 */
public class JFXImageWritable extends JFXImage {

    public JFXImageWritable(int w, int h) {
        this(new WritableImage(w, h));
    }

    public JFXImageWritable(WritableImage image) {
        super(image);
    }

    @Override
    public void setColor(int x, int y, Color color) {
        PixelWriter pixelWriter = ((WritableImage) image).getPixelWriter();
        // pixelWriter is cached...

        pixelWriter.setArgb(x, y, color.getARGB());
    }
}
