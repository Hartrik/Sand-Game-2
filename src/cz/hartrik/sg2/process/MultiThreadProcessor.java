
package cz.hartrik.sg2.process;

import cz.hartrik.sg2.world.World;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static cz.hartrik.sg2.world.Chunk.CHUNK_SIZE;

/**
 * Processor využívající všechna dostupná jádra. Na menším počtu jader bude
 * pomalejší.
 * 
 * @version 2014-03-18
 * @author Patrik Harag
 */
public class MultiThreadProcessor extends BasicProcessor {
    
    // procházení
    private final int poolSize = Runtime.getRuntime().availableProcessors() - 1;
    private final ExecutorService threadPool;
    
    private final List<Callable<Object>> tasks = new ArrayList<>();
    
    private final int bound = 5;
    private final int numberOfTasks;
    
    private final int randDataCount = 100;
    
    private final int randDataWidth;
    private final int randDataExtraWidth;
    
    private final int[][][] randData;
    private final int[][]   randDataExtra;
    
    public MultiThreadProcessor(World world, Tools tools) {
        super(world, tools);
        
        this.threadPool = Executors.newFixedThreadPool(poolSize);
        this.numberOfTasks = world.getWidth() / CHUNK_SIZE;
        
        this.randDataWidth = CHUNK_SIZE - bound;
        this.randDataExtraWidth = world.getWidth() / CHUNK_SIZE * bound;
        
        this.randData      = new int[randDataCount][numberOfTasks][randDataWidth];
        this.randDataExtra = new int[randDataCount][randDataExtraWidth];
        
        initRandomData();
    }
    
    protected final void initRandomData() {
        for (int i = 0; i < randDataCount; i++) {
            // rand data
            for (int j = 0; j < numberOfTasks; j++)
                for (int k = 0; k < randDataWidth; k++)
                    randData[i][j][k] =
                            (j * CHUNK_SIZE) + random.nextInt(randDataWidth);

            // okraje
            int index = 0;
            for (int j = 0; j < numberOfTasks; j++)
                for (int k = 0; k < bound; k++)
                    randDataExtra[i][index++] =
                            (j * CHUNK_SIZE + CHUNK_SIZE - bound)
                            + random.nextInt(bound);
        }
    }
    
    // -----------------------------------------------------
    
    @Override
    protected void iterate(int cyTop, int cyBottom) {
        // procházení odzdola chunku
        for (int y = cyBottom; y >= cyTop; --y) {
            int nextInt = xorRandom.nextInt(randDataCount);
            int[][] data = randData[nextInt];
            
            tasks.clear();
            for (int i = 0; i < numberOfTasks; i++)
                tasks.add(Executors.callable(new Work(y, data[i])));
            
            tasks.add(Executors.callable(new Work(y, randDataExtra[nextInt])));
            
            try {
                threadPool.invokeAll(tasks);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
    private class Work implements Runnable {
        private final int y;
        private final int[] positions;

        public Work(int y, int[] positions) {
            this.positions = positions;
            this.y = y;
        }
        
        @Override
        public void run() {
            for (int x : positions)
                if (running[x / CHUNK_SIZE]) nextPoint(x, y);
        }
        
    }
    
}