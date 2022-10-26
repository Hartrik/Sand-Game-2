package cz.hartrik.sg2.engine;

import cz.hartrik.sg2.engine.render.JFXRenderer;
import cz.hartrik.sg2.world.ElementArea;
import javafx.scene.image.WritableImage;

/**
 * @author Patrik Harag
 * @version 2017-08-06
 */
public class JFXPlatform implements PlatformProvider {

    public static javafx.scene.image.Image asJFXImage(Image image) {
        if (image instanceof JFXImage) {
            return ((JFXImage) image).getImage();
        }

        throw new IllegalArgumentException("Cannot convert to JavaFX image");
    }

    @Override
    public Image createImage(int w, int h) {
        return new JFXImageWritable(w, h);
    }

    @Override
    public Image createImage(String url) {
        return new JFXImage(url);
    }

    @Override
    public Image renderPreview(ElementArea area) {
        int width = area.getWidth();
        int height = area.getHeight();

        WritableImage image = new WritableImage(width, height);

        JFXRenderer renderer = new JFXRenderer(area);
        renderer.updateBuffer();

        image.getPixelWriter().setPixels(
                0, 0, width, height,
                renderer.getPixelFormat(),
                renderer.getBuffer(), 0, (width * 4));

        return new JFXImageWritable(image);
    }
}
