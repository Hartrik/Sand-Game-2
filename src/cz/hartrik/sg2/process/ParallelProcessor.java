package cz.hartrik.sg2.process;

import cz.hartrik.sg2.world.Chunk;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Procesor, který v jednom vlákně volá metodu 
 * {@link Element#doAction(int, int, Tools, World)} a v druhém 
 * {@link Element#doParallel(int, int, Tools, World)}.
 * Počet paralelně procházených elementů je 1/2 oproti elementům procházeným
 * normálním způsobem.
 * 
 * @version 2014-12-28
 * @author Patrik Harag
 */
public class ParallelProcessor extends BasicProcessor {
    
    private final int randDataCount = 100;
    private final int[][] randData;
    private final int[][] randDataPar;
    
    private final ExecutorService exec = Executors.newFixedThreadPool(2);
    
    public ParallelProcessor(World world, Tools tools) {
        super(world, tools);
        
        this.randData = new int[randDataCount][world.getWidth()];
        for (int i = 0; i < randDataCount; i++)
            for (int j = 0; j < world.getWidth(); j++)
                randData[i][j] = random.nextInt(world.getWidth());
        
        this.randDataPar = new int[randDataCount][world.getWidth() / 2];
        for (int i = 0; i < randDataCount; i++)
            for (int j = 0; j < world.getWidth() / 2; j++)
                randDataPar[i][j] = random.nextInt(world.getWidth());
    }
    
    @Override
    protected void iterate(int cyTop, int cyBottom) {
        
        final Runnable main = () -> {
            for (int y = cyBottom; y >= cyTop; --y)
                for (int x : randData[xorRandom.nextInt(randDataCount)])
                    if (running[x / Chunk.CHUNK_SIZE])
                        world.get(x, y).doAction(x, y, tools, world);
        };
        
        final Runnable parallel = () -> {
            for (int y = cyBottom; y >= cyTop; --y)
                for (int x : randDataPar[xorRandom.nextInt(randDataCount)])
                    if (running[x / Chunk.CHUNK_SIZE])
                        world.get(x, y).doParallel(x, y, tools, world);
        };
        
        final Future<?> future1 = exec.submit(main);
        final Future<?> future2 = exec.submit(parallel);
        
        try {
            future1.get();
            future2.get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}