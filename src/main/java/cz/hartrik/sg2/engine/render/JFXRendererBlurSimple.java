
package cz.hartrik.sg2.engine.render;

import cz.hartrik.common.Color;
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
    protected void updateAt(int index) {
        final Color color = getColor(elements[index], temperature[index]);
        index *= 4;

        if (color.equals(Color.WHITE)) {

            final int b = buffer[index]     & 0xFF;
            final int g = buffer[index + 1] & 0xFF;
            final int r = buffer[index + 2] & 0xFF;

            if (r != 255 && g != 255 && b != 255) {

                buffer[index]     = (byte) ((b * fAlpha) + (color.getBlue()  * fAlphaRev));
                buffer[index + 1] = (byte) ((g * fAlpha) + (color.getGreen() * fAlphaRev));
                buffer[index + 2] = (byte) ((r * fAlpha) + (color.getRed()   * fAlphaRev));

                return;
            }
        }

        buffer[index]     = color.getByteBlue();
        buffer[index + 1] = color.getByteGreen();
        buffer[index + 2] = color.getByteRed();
    }

}