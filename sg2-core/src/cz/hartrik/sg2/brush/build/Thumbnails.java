package cz.hartrik.sg2.brush.build;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.brush.ABrushWrapper;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.engine.Image;
import cz.hartrik.sg2.engine.Platform;
import cz.hartrik.sg2.world.Element;
import java.util.function.BiFunction;

/**
 * @version 2017-08-06
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
        Image image = Platform.get().createImage(width, height);

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
                image.setColor(x, y, color);
            }
        }
        return image;
    }

    private static Color getColor(Element element) {
        return (element == null) ? Color.WHITE : element.getColor();
    }

}