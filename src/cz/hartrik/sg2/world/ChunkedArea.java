
package cz.hartrik.sg2.world;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Point;
import cz.hartrik.common.Streams;
import java.util.Iterator;
import java.util.stream.Stream;

import static cz.hartrik.sg2.world.Chunk.CHUNK_SIZE;

/**
 * Pole elementů rozdělené do chunků.
 * 
 * @version 2015-03-16
 * @author Patrik Harag
 */
public class ChunkedArea extends ElementArea {

    private static final long serialVersionUID = 83715083867368_01_002L;
    
    private final Chunk[] chunks;
    private final int horChunkCount;
    private final int verChunkCount;
    
    public ChunkedArea(int width, int height) {
        super(Checker.checkArgument(width, width % CHUNK_SIZE == 0),
             (Checker.checkArgument(height, height % CHUNK_SIZE == 0)));
        
        this.horChunkCount = width / CHUNK_SIZE;
        this.verChunkCount = height / CHUNK_SIZE;
        this.chunks = new Chunk[getChunkCount()];
        
        initChunks();
    }

    public ChunkedArea(ElementArea elementArea) {
        super(elementArea);
        this.horChunkCount = getWidth() / CHUNK_SIZE;
        this.verChunkCount = getHeight() / CHUNK_SIZE;
        this.chunks = new Chunk[getChunkCount()];
        
        initChunks();
    }
    
    private void initChunks() {
        int count = 0;
        for (int y = 0; y < verChunkCount; y++)
            for (int x = 0; x < horChunkCount; x++)
                chunks[x + (y * horChunkCount)] = new Chunk(this, x, y, ++count);
    }
    
    // základní metody...

    public final void setAndChange(int x, int y, Element element) {
        set(x, y, element);
        getChunkAt(x, y).change();
    }
    
    public final void setAndChange(Point point, Element element) {
        setAndChange(point.getX(), point.getY(), element);
    }
    
    // práce s chunky...
    
    public final int getHorChunkCount() { return horChunkCount; }
    public final int getVerChunkCount() { return verChunkCount; }
    public final int getChunkCount() { return horChunkCount * verChunkCount; }
    
    public final Chunk getChunkAt(final int x, final int y) {
        return chunks[(y / CHUNK_SIZE) * horChunkCount + (x / CHUNK_SIZE)];
    }
    
    public final Chunk getChunk(final int x, final int y) {
        return chunks[(y * horChunkCount) + x];
    }
    
    public final void setAllChunks(final boolean changed) {
        for (Chunk chunk : chunks) chunk.change(changed);
    }

    public final Chunk[] getChunks() {
        return chunks;
    }
    
    // různé

    @Override
    public ChunkedAreaTools<? extends ChunkedArea> getTools() {
        return new ChunkedAreaTools<ChunkedArea>(this) {
            @Override
            public ChunkedArea empty(int width, int height) {
                return new ChunkedArea(width, height);
            }
        };
    }

    @Override
    public Inserter<? extends ChunkedArea> getInserter() {
        return new ChunkedAreaInserter<>(this);
    }
    
    // iterátory...
    
    public Iterator<Chunk> iteratorChunk() {
        return new Iterator<Chunk>() {
            private int i;
            @Override public boolean hasNext() { return i < chunks.length; }
            @Override public Chunk next()    { return chunks[i++]; }
        };
    }
    
    public Stream<Chunk> streamChunk() {
        return Streams.stream(iteratorChunk());
    }
    
}