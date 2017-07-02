package cz.hartrik.sg2.world;

import cz.hartrik.sg2.brush.Brush;
import java.util.HashSet;
import java.util.Set;

/**
 * Zprostředkuje vkládání elementů do {@link ChunkedArea}.
 *
 * @version 2016-06-13
 * @author Patrik Harag
 * @param <T> typ pole elementů
 */
public class ChunkedAreaInserter<T extends ChunkedArea>
        extends ElementAreaInserter<T> {

    private final Set<Chunk> chunks;

    public ChunkedAreaInserter(T chunkedArea) {
        super(chunkedArea);
        this.chunks = new HashSet<>();
    }

    @Override
    public boolean insert(int x, int y, Element element) {
        boolean insert = super.insert(x, y, element);
        if (insert)
            collectorAdd(area.getChunkAt(x, y));

        return insert;
    }

    @Override
    public boolean insert(int x, int y, Brush brush) {
        boolean insert = super.insert(x, y, brush);
        if (insert)
            collectorAdd(area.getChunkAt(x, y));

        return insert;
    }

    @Override
    public boolean apply(int x, int y, Brush brush) {
        boolean insert = super.apply(x, y, brush);
        if (insert)
            collectorAdd(area.getChunkAt(x, y));

        return insert;
    }

    // collector

    protected void collectorAdd(Chunk chunk) {
        chunks.add(chunk);
    }

    protected void collectorReset() {
        chunks.clear();
    }

    protected Chunk[] collectorOut() {
        return chunks.toArray(new Chunk[0]);
    }

    @Override
    public void finalizeInsertion() {
        for (Chunk chunk : chunks)
            chunk.change();

        collectorReset();
    }

}
