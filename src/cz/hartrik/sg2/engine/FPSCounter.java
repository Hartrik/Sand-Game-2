
package cz.hartrik.sg2.engine;

/**
 * Velmi jednoduchý FPS counter.
 *
 * @version 2016-06-25
 * @author Patrik Harag
 */
public class FPSCounter {

    private int currentFPS = 0;
    private int FPS = 0;
    private long start = 0;

    public synchronized void tick(final long currentTimeMillis) {
        currentFPS++;
        if (currentTimeMillis - start >= 1000) {
            FPS = currentFPS;
            currentFPS = 0;
            start = currentTimeMillis;
        }
    }

    public synchronized int getFPS() {
        return FPS;
    }

}
