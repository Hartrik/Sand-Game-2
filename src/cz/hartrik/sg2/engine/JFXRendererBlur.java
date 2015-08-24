
package cz.hartrik.sg2.engine;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.ChunkedArea;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.FallingElement;

/**
 * Renderer, který trochu rozmazává body. Pouze však elementy dědící z
 * {@link FallingElement}.
 * 
 * @version 2014-09-27
 * @author Patrik Harag
 */
public class JFXRendererBlur extends JFXRenderer {

    private final float fAlpha    = 0.7f;
    private final float fAlphaRev = 1.0f - fAlpha;
    
    private final boolean[] blur;
    private final boolean[] last;
    
    public JFXRendererBlur(ChunkedArea area) {
        super(area);
        
        this.blur = new boolean[area.getWidth() * area.getHeight()];
        this.last = new boolean[area.getWidth() * area.getHeight()];
    }

    @Override
    protected void updateColor(int index) {
        final Element element = area.getArray()[index];
        final Color color = element.getColor();
        final int dIndex = index * 4;
        
        if (element instanceof Air) {

            if (blur[index]) {

                // pokračuje se v mizení

                final int b = data[dIndex]     & 0xFF;
                final int g = data[dIndex + 1] & 0xFF;
                final int r = data[dIndex + 2] & 0xFF;

                if (r != 255 && g != 255 && b != 255) {
                
                    data[dIndex]     = (byte) ((b * fAlpha) + (color.getBlue()  * fAlphaRev));
                    data[dIndex + 1] = (byte) ((g * fAlpha) + (color.getGreen() * fAlphaRev));
                    data[dIndex + 2] = (byte) ((r * fAlpha) + (color.getRed()   * fAlphaRev));
                
                    return;

                } else {

                    // mizení ukončeno
                }
                
            } else if (last[index] && color.equals(Color.WHITE)) { // zahajuje se mizení
                
                blur[index] = true;
                last[index] = false;
                
                final int b = data[dIndex]     & 0xFF;
                final int g = data[dIndex + 1] & 0xFF;
                final int r = data[dIndex + 2] & 0xFF;

                data[dIndex]     = (byte) ((b * fAlpha) + (color.getBlue()  * fAlphaRev));
                data[dIndex + 1] = (byte) ((g * fAlpha) + (color.getGreen() * fAlphaRev));
                data[dIndex + 2] = (byte) ((r * fAlpha) + (color.getRed()   * fAlphaRev));
                
                return;
            }
        }
        
        // žádné mizení | konec mizení
        
        last[index] = element instanceof FallingElement;
        blur[index] = false;

        data[dIndex]     = color.getByteBlue();
        data[dIndex + 1] = color.getByteGreen();
        data[dIndex + 2] = color.getByteRed();
        
    }
    
}