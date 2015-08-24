
package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.sg2.brush.BrushRandom;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import javafx.scene.image.Image;

/**
 * Jednoduchý štětec s náhledem.
 * 
 * @version 2015-04-04
 * @author Patrik Harag
 */
public class JFXBrushRandom extends BrushRandom implements Thumbnailable {

    public JFXBrushRandom(BrushInfo info, Element... elements) {
        super(info, elements);
    }
    
    @Override
    public Image getThumb(int width, int height) {
        return Thumbnails.createThumb(this, width, height);
    }
    
}