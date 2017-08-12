package cz.hartrik.sg2.engine.render;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Chunk;
import cz.hartrik.sg2.world.ChunkedArea;

/**
 * Zvýrazní aktivní chunky.
 *
 * @version 2015-01-09
 * @author Patrik Harag
 */
public class Highlighter {

    public static final Color DEFAULT_COLOR = new Color(0, 128, 0, 75);

    private final byte[] data;
    private final ChunkedArea area;
    private final Color color;

    public Highlighter(byte[] data, ChunkedArea chunkedArea,
            Color highlightColor) {

        this.data = data;
        this.area = chunkedArea;
        this.color = highlightColor;
    }

    public void highlightActiveChunks() {
        for (Chunk chunk : area.getChunks())
            if (chunk.isChanged())
                highlightChunk(chunk);
    }

    public void highlightChunk(Chunk chunk) {
        for (int x = chunk.getTopLeftX(); x <= chunk.getBottomRightX(); x++) {
            highlight(x, chunk.getTopLeftY());
            highlight(x, chunk.getBottomRightY());
        }

        for (int y = chunk.getTopLeftY() + 1; y < chunk.getBottomRightY(); y++) {
            highlight(chunk.getBottomRightX(), y);
            highlight(chunk.getTopLeftX(), y);
        }
    }

    // - highlight...

    public final void highlight(final int x, final int y) {
        highlight(y * area.getWidth() + x);
    }

    public final void highlight(int index) {
        index *= 4;

        final int b = data[index]     & 0xFF;
        final int g = data[index + 1] & 0xFF;
        final int r = data[index + 2] & 0xFF;

        final float fAlpha = color.getFloatAlpha();
        final float fAlphaRev = 1.0f - fAlpha;

        data[index]     = (byte) ((color.getBlue()  * fAlpha) + (b * fAlphaRev));
        data[index + 1] = (byte) ((color.getGreen() * fAlpha) + (g * fAlphaRev));
        data[index + 2] = (byte) ((color.getRed()   * fAlpha) + (r * fAlphaRev));
    }

    // gettery

    public Color getHighlightColor() {
        return color;
    }

}