
package cz.hartrik.sg2.process;

import cz.hartrik.common.random.XORShiftRandom;
import cz.hartrik.sg2.engine.Processor;
import cz.hartrik.sg2.world.Chunk;
import cz.hartrik.sg2.world.World;
import java.util.Random;

/**
 * Abstraktní třída Processor.
 *
 * @version 2014-05-11
 * @author Patrik Harag
 */
public abstract class BasicProcessor implements Processor {

    protected final World world;
    protected final Tools tools;
    protected final Random random;
    protected final XORShiftRandom xorRandom;

    protected final boolean[] running;

    protected final int horChunkCount;
    protected final int verChunkCount;

    protected int updatedChunks = 0;

    public BasicProcessor(World world, Tools tools) {
        this.world = world;
        this.tools = tools;

        this.horChunkCount = world.getHorChunkCount();
        this.verChunkCount = world.getVerChunkCount();

        this.random = new Random();
        this.xorRandom = new XORShiftRandom();
        this.running = new boolean[horChunkCount];
    }

    // -----------------------------------------------------

    public World getWorld() {
        return world;
    }

    @Override
    public void nextCycle() {
        world.nextCycle();

        int updated = 0;
        for (int cy = verChunkCount - 1; cy >= 0; --cy) {

            // nejvyšší a nejnišší hor. pozice v ch.
            final int cyTop = cy * world.getChunkSize();
            final int cyBottom = cyTop + world.getChunkSize() - 1;

            // zjistí horizontální chunky, které (ne)byly změněny
            boolean somethingChanged = false;
            for (int cx = 0; cx < horChunkCount; cx++) {
                Chunk chunk = world.getChunk(cx, cy);
                if (chunk.isChanged()) {
                    updated++;
                    somethingChanged = true;
                    running[cx] = true;
                    chunk.change(false); // a pokud se nezmění, tak bude v
                } else {                  // příštím kole nečinný
                    boolean test = testChunk(cx, cyTop, cyBottom);
                    running[cx] = test;
                    if (test) somethingChanged = true;
                }
            }

            if (!somethingChanged) continue;

            iterate(cyTop, cyBottom);

            // kontrola chunků, které se nezměnily
            //  - v jednom kole se totiž nutně nemusí změnit vše co může a
            //    některé částečky by tak mohly zůstat ve vzduchu atd.
            for (int cx = 0; cx < horChunkCount; cx++) {
                Chunk chunk = world.getChunk(cx, cy);
                if (running[cx] && !chunk.isChanged())
                    if (testChunkAll(cx, cyTop, cyBottom)) chunk.change();
            }
        }
        this.updatedChunks = updated;
    }

    protected abstract void iterate(int cyTop, int cyBottom);

    // testování
    // -----------------------------------------------------

    protected final boolean testChunk(
            final int cx,
            final int cyTop, final int cyBottom) {

        final int cxLeft = cx * world.getChunkSize();
        final int cxRight = cxLeft + world.getChunkSize() - 1;

        // horní a dolní okraje
        for (int x = cxLeft; x <= cxRight; x++)
            if (testPoint(x, cyTop) || testPoint(x, cyBottom)) return true;

        // boční okraje
        for (int y = cyBottom; y >= cyTop; --y)
            if (testPoint(cxLeft, y) || testPoint(cxRight, y)) return true;

        return false;
    }

    protected final boolean testChunkAll(
            final int cx,
            final int cyTop, final int cyBottom) {

        final int cxLeft = cx * world.getChunkSize();
        final int cxRight = cxLeft + world.getChunkSize() - 1;

        for (int y = cyBottom; y >= cyTop; --y)
            for (int x = cxLeft; x <= cxRight; x++)
                if (testPoint(x, y)) return true;

        return false;
    }

    protected final boolean testPoint(final int x, final int y) {
        return world.get(x, y).testAction(x, y, tools, world);
    }

    // procházení "na ostro"
    // -----------------------------------------------------

    protected final void nextPoint(final int x, final int y) {
        world.get(x, y).doAction(x, y, tools, world);
    }

    // ostatní

    @Override
    public int getLastUpdatedChunks() {
        return updatedChunks;
    }

    @Override
    public Tools getTools() {
        return tools;
    }

}