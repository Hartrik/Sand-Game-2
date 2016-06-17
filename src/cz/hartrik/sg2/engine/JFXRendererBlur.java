package cz.hartrik.sg2.engine;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.ChunkedArea;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.FallingElement;

/**
 * Renderer, který trochu rozmazává elementy. Pouze však elementy dědící z
 * {@link FallingElement}.
 *
 * @version 2016-03-22
 * @author Patrik Harag
 */
public class JFXRendererBlur extends JFXRenderer {

    // výchozí průhlednost (1 = neprůhledné)
    private static final float ALPHA = 0.7f;
    private static final float WHITE_BACKGROUND = 255 * (1.0f - ALPHA);

    // prvek pole říká, jestli se daný pixel rozmazává
    private final boolean[] blur;

    // prvek pole říká, jestli se při minulém vykreslování na daném místě
    // nacházel element, který se rozmazává
    private final boolean[] last;

    public JFXRendererBlur(ChunkedArea area) {
        super(area);

        this.blur = new boolean[area.getWidth() * area.getHeight()];
        this.last = new boolean[area.getWidth() * area.getHeight()];
    }

    @Override
    protected void updateAt(int index) {
        final Element element = elements[index];
        final Color color = getColor(element, temperature[index]);
        final int i = index * 4;

        if (element instanceof Air) {

            if (last[index] && color.equals(Color.WHITE)) {
                // zahajuje se postupé mizení

                blur[index] = true;
                last[index] = false;
            }

            if (blur[index]) {
                // pokračuje se v mizení

                final int b = buffer[i]     & 0xFF;
                final int g = buffer[i + 1] & 0xFF;
                final int r = buffer[i + 2] & 0xFF;

                if (isVisible(r, g, b)) {
                    buffer[i]     = (byte) ((b * ALPHA) + WHITE_BACKGROUND);
                    buffer[i + 1] = (byte) ((g * ALPHA) + WHITE_BACKGROUND);
                    buffer[i + 2] = (byte) ((r * ALPHA) + WHITE_BACKGROUND);

                    return;
                }
            }
        }

        // bez rozmazání

        last[index] = element instanceof FallingElement;
        blur[index] = false;

        buffer[i]     = color.getByteBlue();
        buffer[i + 1] = color.getByteGreen();
        buffer[i + 2] = color.getByteRed();
    }

    private boolean isVisible(int r, int g, int b) {
        return r < 250 && g < 250 && b < 250;
    }

}