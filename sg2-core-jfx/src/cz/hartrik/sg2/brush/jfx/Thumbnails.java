package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.brush.ABrushWrapper;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.world.Element;
import java.util.function.BiFunction;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * @version 2015-11-19
 * @author Patrik Harag
 */
public final class Thumbnails {

    private Thumbnails() { }

    public static class ThumbnailableBrush
            extends ABrushWrapper implements Thumbnailable {

        private final BiFunction<Integer, Integer, Image> imageSupplier;

        ThumbnailableBrush(Brush brush,
                BiFunction<Integer, Integer, Image> imageSupplier) {

            super(brush);
            this.imageSupplier = imageSupplier;
        }

        @Override
        public Image getThumb(int width, int height) {
            return imageSupplier.apply(width, height);
        }
    }

    /**
     * Vytvoří nový štětec s náhledem tím, že zabalí jiný štětec.
     *
     * @param image náhled
     * @param brush štětec
     * @return štětec s náhledem
     */
    public static Thumbnailable addThumb(Image image, Brush brush) {
        return new ThumbnailableBrush(brush, (w, h) -> image);
    }

    /**
     * Vytvoří nový štětec s náhledem tím, že zabalí jiný štětec.
     * Náhled vytvoří podle barev elementů.
     *
     * @see #generateThumb(Brush, int, int)
     * @param brush štětec
     * @return štětec s náhledem
     */
    public static Thumbnailable addGeneratedThumb(Brush brush) {
        return new ThumbnailableBrush(brush, (w, h) -> generateThumb(brush, w, h));
    }

    public static Image generateThumb(Brush brush, int width, int height) {
        final WritableImage image = new WritableImage(width, height);
        final PixelWriter pixelWriter = image.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color;
                if (brush.isAdvanced()) {
                    Element element = brush.getElement(null, x, y, null, null);

                    color = (element == null)
                            ? getColor(brush.getElement())
                            : element.getColor();
                } else {
                    color = getColor(brush.getElement());
                }
                pixelWriter.setArgb(x, y, color.getARGB());
            }
        }
        return image;
    }

    private static Color getColor(Element element) {
        return (element == null) ? Color.WHITE : element.getColor();
    }

}