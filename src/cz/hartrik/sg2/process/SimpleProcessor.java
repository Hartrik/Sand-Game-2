
package cz.hartrik.sg2.process;

import cz.hartrik.sg2.world.World;

/**
 * Jednoduchý processor běžící v jednom vlákně.
 *
 * @version 2014-03-18
 * @author Patrik Harag
 */
public final class SimpleProcessor extends BasicProcessor {

    private final int randDataCount = 100;
    private final int[][] randData;

    public SimpleProcessor(World world, Tools tools) {
        super(world, tools);

        this.randData = new int[randDataCount][world.getWidth()];
        for (int i = 0; i < randDataCount; i++)
            for (int j = 0; j < world.getWidth(); j++)
                randData[i][j] = random.nextInt(world.getWidth());
    }

    @Override
    protected void iterate(int cyTop, int cyBottom, int runningCount) {
        for (int y = cyBottom; y >= cyTop; --y)
            for (int x : randData[xorRandom.nextInt(randDataCount)])
                if (running[x / world.getChunkSize()]) nextPoint(x, y);
    }

}