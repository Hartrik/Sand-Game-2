
package cz.hartrik.sg2.world;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Point;
import cz.hartrik.common.Streams;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Pole elementů rozdělené do chunků.
 *
 * @version 2016-06-21
 * @author Patrik Harag
 */
public class ChunkedArea extends ElementArea {

    private static final long serialVersionUID = 83715083867368_01_002L;

    private final int chunkSize;
    private final Chunk[] chunks;
    private final int horChunkCount;
    private final int verChunkCount;

    public ChunkedArea(int width, int height, int chunkSize) {
        super(width, height);

        Checker.checkArgument(width, width % chunkSize == 0,
                "width % chunkSize != 0");
        Checker.checkArgument(height, height % chunkSize == 0,
                "height % chunkSize != 0");

        this.chunkSize = chunkSize;
        this.horChunkCount = width / chunkSize;
        this.verChunkCount = height / chunkSize;
        this.chunks = new Chunk[getChunkCount()];

        initChunks();
    }

    public ChunkedArea(ChunkedArea elementArea) {
        super(elementArea);

        this.chunkSize = elementArea.getChunkSize();
        this.horChunkCount = elementArea.getHorChunkCount();
        this.verChunkCount = elementArea.getVerChunkCount();
        this.chunks = new Chunk[getChunkCount()];

        initChunks();
    }

    private void initChunks() {
        for (int y = 0; y < verChunkCount; y++)
            for (int x = 0; x < horChunkCount; x++)
                chunks[x + (y * horChunkCount)] = new Chunk(this, chunkSize, x, y);
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

    public int getChunkSize() {
        return chunkSize;
    }

    public final int getHorChunkCount() { return horChunkCount; }
    public final int getVerChunkCount() { return verChunkCount; }
    public final int getChunkCount() { return horChunkCount * verChunkCount; }

    public final Chunk getChunkAt(final int x, final int y) {
        return chunks[(y / chunkSize) * horChunkCount + (x / chunkSize)];
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
            public ChunkedArea empty(int width, int height, int chunkSize) {
                return new ChunkedArea(width, height, chunkSize);
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