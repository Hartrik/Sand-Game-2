package cz.hartrik.sg2.process;

import cz.hartrik.sg2.world.Chunk;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import java.util.concurrent.*;

import static cz.hartrik.sg2.world.Chunk.CHUNK_SIZE;

/**
 * Prochází elementy a zároveň se stará o zpracování tepla.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public class HeatProcessor extends BasicProcessor {

    private final int randDataCount = 100;
    private final int[][] randData;

    private final ExecutorService exec = Executors.newFixedThreadPool(2);
    private Future<?> future1;
    private Future<?> future2;

    public HeatProcessor(World world, Tools tools) {
        super(world, tools);

        this.randData = new int[randDataCount][world.getWidth()];
        for (int i = 0; i < randDataCount; i++)
            for (int j = 0; j < world.getWidth(); j++)
                randData[i][j] = random.nextInt(world.getWidth());
    }

    @Override
    protected void iterate(int cyTop, int cyBottom) {
        iterateE(cyTop, cyBottom, running);

        // po dokončení se zpracuje teplota

        // než začneme zpracovávat další blok, předchozí musí být dokončen
        try {
            if (future1 != null) future1.get(10, TimeUnit.SECONDS);
            if (future2 != null) future2.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            throw new RuntimeException(ex);
        }

        boolean[] clone = running.clone();

        Runnable runnable1 = () -> {
            iterateT(cyTop, cyTop + Chunk.CHUNK_SIZE / 2 - 1, clone);
        };

        Runnable runnable2 = () -> {
            iterateT(cyTop + Chunk.CHUNK_SIZE / 2 , cyBottom, clone);
        };

        future1 = exec.submit(runnable1);
        future2 = exec.submit(runnable2);
    }

    private void iterateE(int cyTop, int cyBottom, boolean[] running) {
        for (int y = cyBottom; y >= cyTop; --y)
            for (int x : randData[xorRandom.nextInt(randDataCount)])
                if (running[x / CHUNK_SIZE]) nextPoint(x, y);
    }

    private void iterateT(final int cyTop, final int cyBottom, boolean[] running) {
        for (int y = cyBottom; y >= cyTop; --y) {
            iterateT(y, running);
        }
    }

    private void iterateT(final int y, final boolean[] running) {
        for (int h = 0; h < horChunkCount; h++) {
            if (!running[h])
                continue;

            final int to = CHUNK_SIZE * (h + 1);
            for (int x = CHUNK_SIZE * h; x < to; x++) {
                processTemperature(x, y);
            }
        }
    }

    private void processTemperature(final int x, final int y) {
        final Element element = world.get(x, y);
        if (!element.isConductive()) return;

        final int rnd = tools.randomInt(4);
        final int ix = x + Direction.MAIN_DIRECTIONS[rnd];
        final int iy = y + Direction.MAIN_DIRECTIONS[rnd + 1];

        if (world.valid(ix, iy) && world.get(ix, iy).hasTemperature()) {
            final float nTemp = world.getTemperature(ix, iy);
            final float eTemp = world.getTemperature(x, y);

            final float temp = element.getConductiveIndex() * nTemp
                    + (1 - element.getConductiveIndex()) * eTemp;

            final float lTemp = temp - temp * element.loss();

            // + = vzýšení teploty
            final float diff = temp - eTemp;

            world.addTemperature(ix, iy, -diff);

            if (lTemp < 1)
                world.setTemperature(x, y, 0);
            else
                world.setTemperature(x, y, lTemp);
        } else {
            final float temp = world.getTemperature(x, y);
            final float lTemp = temp - temp * element.loss();

            if (lTemp < 1)
                world.setTemperature(x, y, 0);
            else
                world.setTemperature(x, y, lTemp);
        }
    }

}