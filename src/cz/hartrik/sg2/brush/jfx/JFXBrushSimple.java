
package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.brush.BrushSimple;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.factory.ISingleInputFactory;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Jednoduchý štětec s náhledem pro JavuFX.
 * 
 * @version 2014-05-22
 * @author Patrik Harag
 */
public class JFXBrushSimple extends BrushSimple implements Thumbnailable {

    public JFXBrushSimple(
            BrushInfo brushInfo,
            ISingleInputFactory<Color, ? extends Element> factory,
            Color... colors) {
        
        super(brushInfo, factory, colors);
    }

    @Override
    public Image getThumb(int width, int height) {
        final WritableImage image = new WritableImage(width, height);
        if (colors.length == 0) return image;
        
        final PixelWriter pixelWriter = image.getPixelWriter();
        
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                pixelWriter.setArgb(x, y, collorSupplier.get().getARGB());
        
        return image;
    }
    
}