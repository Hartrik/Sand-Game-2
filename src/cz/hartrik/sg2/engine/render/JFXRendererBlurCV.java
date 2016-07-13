package cz.hartrik.sg2.engine.render;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.ChunkedArea;

/**
 * Renderer s motion blur a zvýrazněním aktivních chunků.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public class JFXRendererBlurCV extends JFXRendererBlur {

    private final Highlighter highlighter;

    public JFXRendererBlurCV(ChunkedArea chunkedArea) {
        this(chunkedArea, Highlighter.DEFAULT_COLOR);
    }

    public JFXRendererBlurCV(ChunkedArea chunkedArea, Color color) {
        super(chunkedArea);

        this.highlighter = new Highlighter(buffer, chunkedArea, color);
    }

    @Override
    public synchronized void updateBuffer() {
        super.updateBuffer();
        highlighter.highlightActiveChunks();
    }

}