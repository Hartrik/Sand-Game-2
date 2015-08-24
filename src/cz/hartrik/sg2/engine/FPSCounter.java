
package cz.hartrik.sg2.engine;

/**
 * Velmi jednoduchý FPS counter.
 * 
 * @version 2014-03-02
 * @author Patrik Harag
 */
public class FPSCounter {

    private int currentFPS = 0;
    private int FPS = 0;
    private long start = 0;

    public void tick() {
        currentFPS++;
        if (System.currentTimeMillis() - start >= 1000) {
            FPS = currentFPS;
            currentFPS = 0;
            start = System.currentTimeMillis();
        }
    }

    public int getFPS() {
        return FPS;
    }
    
}
