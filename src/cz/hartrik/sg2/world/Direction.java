
package cz.hartrik.sg2.world;

import java.io.Serializable;

/**
 * Slouží k označení pozice v poli, vzhledem k určitému bodu.
 * 
 * @version 2014-03-24
 * @author Patrik Harag
 */
public enum Direction implements Serializable {
    
    UP(0, -1, true), DOWN(0, 1, true), LEFT(-1, 0, true), RIGHT(1, 0, true),
    UP_LEFT(-1, -1), UP_RIGHT(1, -1), DOWN_LEFT(-1, 1), DOWN_RIGHT(1, 1);
        
    private final int x, y;
    private final boolean main;

    private Direction(int x, int y) {
        this(x, y, false);
    }
    
    private Direction(int x, int y, boolean main) {
        this.x = x;
        this.y = y;
        this.main = main;
    }

    /**
     * Vrací relativní horizontální pozici bodu v tomto směru v poli.
     * 
     * @return -1, 0, 1
     */
    public int getX() {
        return x;
    }
    
    /**
     * Vrací relativní vertikální pozici bodu v tomto směru v poli.
     * 
     * @return -1, 0, 1
     */
    public int getY() { return y; }

    /**
     * Vrací true, pokud je tento směr hlavní - nahoru, dolů, vlevo, vpravo.
     * 
     * @see #UP
     * @see #DOWN
     * @see #LEFT
     * @see #RIGHT
     * @return hlavní směr
     */
    public boolean isMain() {
        return main;
    }
    
}