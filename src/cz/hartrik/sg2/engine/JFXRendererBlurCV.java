package cz.hartrik.sg2.engine;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Chunk;
import cz.hartrik.sg2.world.ChunkedArea;

/**
 * @version 2015-01-09
 * @author Patrik Harag
 */
public class JFXRendererBlurCV extends JFXRendererBlur {

    private final Highlighter highlighter;
    
    public JFXRendererBlurCV(ChunkedArea chunkedArea) {
        this(chunkedArea, Highlighter.DEFAULT_COLOR);
    }
    
    public JFXRendererBlurCV(ChunkedArea chunkedArea, Color color) {
        super(chunkedArea);
        
        this.highlighter = new Highlighter(data, chunkedArea, color);
    }
    
    @Override @Deprecated
    public synchronized void updateBuffer(Chunk[] chunks) {
        super.updateBuffer(chunks);
        highlighter.highlightActiveChunks();
    }
    
    @Override
    public synchronized void updateBufferAll() {
        super.updateBufferAll();
        highlighter.highlightActiveChunks();
    }
    
}