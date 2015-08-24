
package cz.hartrik.sg2.process;

import cz.hartrik.sg2.engine.Processor;
import cz.hartrik.sg2.world.BasicElement;
import cz.hartrik.sg2.world.World;

/**
 * Testuje výkon a vlastnosti vykreslování.
 * 
 * @version 2014-03-07
 * @author Patrik Harag
 */
public class _TestPerformanceProcessor implements Processor {

    private final World world;
    
    private int cycle = -40;

    public _TestPerformanceProcessor(World world) {
        this.world = world;
    }
    
    @Override
    public void nextCycle() {
        if (++cycle == 40) cycle = -40;
        update(cycle);
    }
    
    private void update(int odd) {
        for (int y = 0; y < world.getHeight(); y++) {
            if (++odd == 40) odd = -40;

            if (odd > -1) {
                for (int x = 0; x < world.getWidth(); x++)
                    world.set(x, y, BasicElement.WALL);
            } else {
                 for (int x = 0; x < world.getWidth(); x++)
                    world.set(x, y, BasicElement.AIR);
            }
        }
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public int getLastUpdatedChunks() {
        return world.getChunks().length;
    }
    
}