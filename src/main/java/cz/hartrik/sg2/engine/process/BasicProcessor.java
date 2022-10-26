
package cz.hartrik.sg2.engine.process;

import cz.hartrik.sg2.engine.Processor;
import cz.hartrik.sg2.random.XORShiftRandom;
import cz.hartrik.sg2.world.Chunk;
import cz.hartrik.sg2.world.World;
import java.util.Random;

/**
 * Abstraktní třída Processor.
 *
 * @version 2017-08-12
 * @author Patrik Harag
 */
public abstract class BasicProcessor implements Processor {

    protected final World world;
    protected final Tools tools;
    protected final Random random;
    protected final XORShiftRandom xorRandom;

    // značí chunky které jsou během aktuálního cyklu procházeny
    protected final boolean[][] runningAll;
    protected boolean[] running;

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
        this.runningAll = new boolean[verChunkCount][horChunkCount];
        this.running = new boolean[horChunkCount];
    }

    // -----------------------------------------------------

    public World getWorld() {
        return world;
    }

    @Override
    public void nextCycle() {
        world.nextCycle();

        // sesbírání aktivních chunků
        for (int cy = 0; cy < verChunkCount; cy++) {
            running = runningAll[cy];
            for (int cx = 0; cx < horChunkCount; cx++) {
                running[cx] = world.getChunk(cx, cy).isChanged();
            }
        }

        int updated = 0;
        for (int cy = verChunkCount - 1; cy >= 0; --cy) {
            this.running = runningAll[cy];

            // zjistí horizontální chunky, které (ne)byly změněny
            int runningCount = 0;

            for (int cx = 0; cx < horChunkCount; cx++) {
                Chunk chunk = world.getChunk(cx, cy);
                if (chunk.isChanged()) {
                    runningCount++;
                    chunk.change(false);
                    // a pokud se nezmění, tak už bude v dalším kole nečinný

                } else {
                    // jestliže je chunk nezměněný, tak ho stejně alespoň
                    // zrychleně otestujeme a případně také projdeme

                    // pokud bychom to netestovali, tak by např. padající
                    // elementy zůstali viset ve vzduchu pokud bychom pod nimi
                    // něco vymazali tak, že by nedošlo ke změně jejich chunku

                    if (testChunkFast(chunk)) {
                        running[cx] = true;
                        runningCount++;
                    }
                }
            }

            if (runningCount == 0)
                continue;

            updated += runningCount;

            // nejvyšší a nejnišší hor. pozice v ch.
            int cyTop = cy * world.getChunkSize();
            int cyBottom = cyTop + world.getChunkSize() - 1;

            iterate(cyTop, cyBottom, runningCount);

            // kontrola chunků, které se nezměnily
            for (int cx = 0; cx < horChunkCount; cx++) {
                Chunk chunk = world.getChunk(cx, cy);
                if (running[cx] && !chunk.isChanged()) {
                    // tento chunk by mohlo jít uspat, ale ještě ho musíme
                    // pořádně otestovat - v jednom kole se totiž nutně nemusí
                    // změnit vše co může a některé částečky by tak mohly zůstat
                    // ve vzduchu atd.
                    if (testChunkFull(chunk)) {
                        // chunk nemůže být uspát - probudíme ho
                        chunk.change();
                    }
                }
            }
        }
        this.updatedChunks = updated;
    }

    protected abstract void iterate(int cyTop, int cyBottom, int runningCount);

    // testování
    // -----------------------------------------------------

    protected final boolean testChunkFast(Chunk chunk) {
        // testuje pouze pokud je vedle aktivní chunk

        // levá strana
        if (chunk.getChunkX() > 0 && running[chunk.getChunkX() - 1]) {
            for (int y = chunk.getTopLeftY() + 1; y < chunk.getBottomRightY(); y++)
                if (testPoint(chunk.getTopLeftX(), y)) return true;
        }

        // pravá strana
        if (chunk.getChunkX() < (horChunkCount - 1) && running[chunk.getChunkX() + 1]) {
            for (int y = chunk.getTopLeftY() + 1; y < chunk.getBottomRightY(); y++)
                if (testPoint(chunk.getBottomRightX(), y)) return true;
        }

        // horní strana
        if (chunk.getChunkY() > 0 && runningAll[chunk.getChunkY() - 1][chunk.getChunkX()]) {
            for (int x = chunk.getTopLeftX(); x <= chunk.getBottomRightX(); x++)
                if (testPoint(x, chunk.getTopLeftY())) return true;
        }

        // dolní strana
        if (chunk.getChunkY() < (verChunkCount - 1) && runningAll[chunk.getChunkY() + 1][chunk.getChunkX()]) {
            for (int x = chunk.getTopLeftX(); x <= chunk.getBottomRightX(); x++)
                if (testPoint(x, chunk.getBottomRightY())) return true;
        }

        return false;
    }

    protected final boolean testChunkFull(Chunk chunk) {
        for (int y = chunk.getTopLeftY(); y <= chunk.getBottomRightY(); y++)
            for (int x = chunk.getTopLeftX(); x <= chunk.getBottomRightX(); x++)
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