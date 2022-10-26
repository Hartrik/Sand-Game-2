package cz.hartrik.sg2.engine;

import cz.hartrik.common.Color;
import javafx.scene.image.PixelReader;

/**
 * @author Patrik Harag
 * @version 2017-08-06
 */
public class JFXImage implements Image {

    protected final javafx.scene.image.Image image;

    public JFXImage(String url) {
        this.image = new javafx.scene.image.Image(url);
    }

    public JFXImage(javafx.scene.image.Image image) {
        this.image = image;
    }

    @Override
    public int getWidth() {
        return (int) image.getWidth();
    }

    @Override
    public int getHeight() {
        return (int) image.getHeight();
    }

    @Override
    public Color getColor(int x, int y) {
        PixelReader pixelReader = image.getPixelReader();
        // pixelReader is cached...

        return  Color.createARGB(pixelReader.getArgb(x, y));
    }

    @Override
    public void setColor(int x, int y, Color color) {
        throw new UnsupportedOperationException();
    }

    public javafx.scene.image.Image getImage() {
        return image;
    }
}
