
package cz.hartrik.sg2.engine;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.ChunkedArea;

/**
 * Renderer vyznačující aktivní chunky. Podporuje průhledné barvy.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public class JFXRendererCV extends JFXRenderer {

    private final Highlighter highlighter;

    public JFXRendererCV(ChunkedArea chunkedArea) {
        this(chunkedArea, Highlighter.DEFAULT_COLOR);
    }

    public JFXRendererCV(ChunkedArea chunkedArea, Color color) {
        super(chunkedArea);

        this.highlighter = new Highlighter(buffer, chunkedArea, color);
    }

    @Override
    public synchronized void updateBuffer() {
        super.updateBuffer();
        highlighter.highlightActiveChunks();
    }

}