
package cz.hartrik.sg2.engine;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;

/**
 * Renderer, který trochu rozmazává body.
 * 
 * @version 2014-09-22
 * @author Patrik Harag
 */
public class JFXRendererBlurSimple extends JFXRenderer {

    private final float fAlpha    = 0.7f;
    private final float fAlphaRev = 1.0f - fAlpha;
    
    public JFXRendererBlurSimple(ElementArea area) {
        super(area);
    }

    @Override
    protected void updateColor(int index) {
        final Element[] array = area.getArray();
        
        final Color color = array[index].getColor();
        index *= 4;
        
        if (color.equals(Color.WHITE)) {
            
            final int b = data[index]     & 0xFF;
            final int g = data[index + 1] & 0xFF;
            final int r = data[index + 2] & 0xFF;

            if (r != 255 && g != 255 && b != 255) {
                
                data[index]     = (byte) ((b * fAlpha) + (color.getBlue()  * fAlphaRev));
                data[index + 1] = (byte) ((g * fAlpha) + (color.getGreen() * fAlphaRev));
                data[index + 2] = (byte) ((r * fAlpha) + (color.getRed()   * fAlphaRev));
                
                return;
            }
        }
            
        data[index]     = color.getByteBlue();
        data[index + 1] = color.getByteGreen();
        data[index + 2] = color.getByteRed();
    }
    
}