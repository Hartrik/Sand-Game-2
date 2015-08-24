package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.brush.SourceableBrush;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.element.type.Sourceable;
import java.util.function.Supplier;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * @version 2015-04-04
 * @author Patrik Harag
 */
public class Thumbnails {
    
    private static class ThumbnailableBrush implements Thumbnailable {

        protected final Image image;
        protected final Brush brush;
        
        public ThumbnailableBrush(Brush brush, Image image) {
            this.image = image;
            this.brush = brush;
        }

        @Override
        public Image getThumb(int width, int height) {
            return image;
        }

        @Override
        public Element getElement(Element current) {
            return brush.getElement(current);
        }

        @Override
        public Element getElement(Element current, int x, int y,
                ElementArea area, Controls controls) {
            
            return brush.getElement(current, x, y, area, controls);
        }

        @Override
        public BrushInfo getInfo() {
            return brush.getInfo();
        }

        @Override
        public boolean isAdvanced() {
            return brush.isAdvanced();
        }

        @Override
        public boolean isProducer(Element element) {
            return brush.isProducer(element);
        }
    }
    
    private static class ThumbnailableBrushSourceable
            extends ThumbnailableBrush implements SourceableBrush {

        public ThumbnailableBrushSourceable(Brush brush, Image image) {
            super(brush, image);
        }

        @Override
        public Supplier<Sourceable> getSourceSupplier() {
            return ((SourceableBrush) brush).getSourceSupplier();
        }
    }
    
    /**
     * Zachovává rozhraní {@link SourceableBrush}.
     * 
     * @param image
     * @param brush
     * @return 
     */
    public static Thumbnailable addThumb(Image image, Brush brush) {
        return (brush instanceof SourceableBrush)
            ? new ThumbnailableBrushSourceable(brush, image)
            : new ThumbnailableBrush(brush, image);
    }
    
    public static Image createThumb(Brush brush, int width, int height) {
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
        return element == null
                ? Color.WHITE
                : element.getColor();
    }
    
}