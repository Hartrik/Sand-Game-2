
package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.sg2.brush.ABrushSource;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.factory.SourceFactory;
import javafx.scene.image.Image;

/**
 * Štětec vytvářející zdroje s náhledem.
 * 
 * @version 2014-05-06
 * @author Patrik Harag
 */
public class JFXBrushSource extends ABrushSource implements Thumbnailable {

    protected static final SourceFactory factory = new SourceFactory();
    public static SourceFactory getFactoryInstance() {
        return factory;
    }

    protected final Image image;

    public JFXBrushSource(BrushInfo desc, String thumbUrl, int ratioChance) {
        this(desc, new Image(thumbUrl), ratioChance);
    }

    public JFXBrushSource(BrushInfo desc, Image image, int ratioChance) {
        super(desc, ratioChance);
        this.image = image;
    }

    @Override
    public Image getThumb(int width, int height) {
        return image;
    }

    @Override
    public SourceFactory getFactory() {
        return factory;
    }

}