
package cz.hartrik.sg2.engine;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Chunk;
import cz.hartrik.sg2.world.ChunkedArea;

/**
 * Renderer vyznačující aktivní chunky. Podporuje průhledné barvy.
 * 
 * @version 2015-01-09
 * @author Patrik Harag
 */
public class JFXRendererCV extends JFXRenderer {

    private final Highlighter highlighter;
    
    public JFXRendererCV(ChunkedArea chunkedArea) {
        this(chunkedArea, Highlighter.DEFAULT_COLOR);
    }
    
    public JFXRendererCV(ChunkedArea chunkedArea, Color color) {
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