
package cz.hartrik.sg2.random;

import java.io.Serializable;

/**
 * Jednoduchý, ale rychlý generátor náhodných čísel.
 * 
 * @version 2014-02-25
 * @author Patrik Harag
 */
public class XORShiftRandom implements Serializable {
    
    private static final long serialVersionUID = 10411599111108_11_001L;
    public static final XORShiftRandom RANDOM = new XORShiftRandom();
    
    private long last;

    public XORShiftRandom() {
        this(System.nanoTime());
    }

    public XORShiftRandom(long seed) {
        this.last = seed;
    }

    public final int nextInt(int max) {
        last ^= (last << 21);
        last ^= (last >>> 35);
        last ^= (last << 4);
        
        final int out = (int) last % max;     
        return (out < 0) ? -out : out;
    }

    public final boolean nextBoolean() {
        return nextInt(2) != 1;
    }
    
    // statické
    
    public static int nextInt(long seed, int max) {
        seed ^= (seed << 21);
        seed ^= (seed >>> 35);
        seed ^= (seed << 4);
        
        final int out = (int) seed % max;
        return (out < 0) ? -out : out;
    }
    
}