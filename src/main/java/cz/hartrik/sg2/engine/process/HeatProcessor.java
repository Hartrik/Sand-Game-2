package cz.hartrik.sg2.engine.process;

import cz.hartrik.sg2.engine.ThreadFactoryName;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import java.util.concurrent.*;

/**
 * Prochází elementy a zároveň se stará o zpracování tepla.
 *
 * @version 2016-06-26
 * @author Patrik Harag
 */
public class HeatProcessor extends BasicProcessor {

    private static final ThreadFactory TF
            = new ThreadFactoryName("SG2 - heat processing [%03d]");

    private final int randDataCount = 100;
    private final int[][] randData;

    // toto pole je znovupoužíváno kvůli výkonu, na který má nezanedbatelný vliv
    private final int[] runningChunksIndex;

    private final ExecutorService exec = Executors.newFixedThreadPool(2, TF);
    private Future<?> future1;
    private Future<?> future2;

    public HeatProcessor(World world, Tools tools) {
        super(world, tools);

        this.randData = new int[randDataCount][world.getChunkSize()];
        for (int i = 0; i < randDataCount; i++)
            for (int j = 0; j < world.getChunkSize(); j++)
                randData[i][j] = random.nextInt(world.getChunkSize());

        this.runningChunksIndex = new int[horChunkCount];
    }

    @Override
    protected void iterate(int cyTop, int cyBottom, int runningCount) {
        iterateE(cyTop, cyBottom, running, runningCount);

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
            iterateT(cyTop, cyTop + world.getChunkSize() / 2 - 1, clone);
        };

        Runnable runnable2 = () -> {
            iterateT(cyTop + world.getChunkSize() / 2 , cyBottom, clone);
        };

        future1 = exec.submit(runnable1);
        future2 = exec.submit(runnable2);
    }

    private void iterateE(
            int cyTop, int cyBottom, boolean[] running, int runningCount) {

        if (runningCount == 1) {
            // v řádku je pouze jeden aktivní chunk

            int idx = 0;

            for (int i = 0; i < running.length; i++) {
                if (running[i]) {
                    idx = i * world.getChunkSize();
                    break;
                }
            }

            for (int y = cyBottom; y >= cyTop; --y)
                for (int c : randData[xorRandom.nextInt(randDataCount)])
                    nextPoint(idx + c, y);

        } else {
            // v řádku je více aktivních chunků

            // je nutné střídat procházení zleva a zprava, protože při malých
            // velikostech chunků by některé elementy mohly táhnout na jednu
            // starnu (především voda)
            if (xorRandom.nextBoolean()) {
                for (int i = 0, j = 0; i < running.length; i++)
                    if (running[i])
                        runningChunksIndex[j++] = i * world.getChunkSize();
            } else {
                for (int i = (running.length - 1), j = 0; i >= 0; i--)
                    if (running[i])
                        runningChunksIndex[j++] = i * world.getChunkSize();
            }

            for (int y = cyBottom; y >= cyTop; --y) {
                for (int i = 0; i < runningCount; i++) {
                    final int idx = runningChunksIndex[i];
                    for (int c : randData[xorRandom.nextInt(randDataCount)]) {
                        nextPoint(idx + c, y);
                    }
                }
            }
        }
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

            final int to = world.getChunkSize() * (h + 1);
            for (int x = world.getChunkSize() * h; x < to; x++) {
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

    @Override
    public void shutdown() {
        exec.shutdown();
    }

}