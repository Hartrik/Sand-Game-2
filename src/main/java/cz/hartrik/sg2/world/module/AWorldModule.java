package cz.hartrik.sg2.world.module;

import cz.hartrik.sg2.world.ElementArea;

/**
 * Abstraktní třída modulu s počítadlem.
 * 
 * @version 2015-03-07
 * @author Patrik Harag
 * @param <T> typ
 */
public abstract class AWorldModule<T extends ElementArea>
        implements WorldModule<T> {

    private final int refreshCycle;
    private int i = 0;

    public AWorldModule(int refreshCycle) {
        this.refreshCycle = refreshCycle;
    }
    
    @Override
    public boolean nextCycle(T area) {
        if (++i >= refreshCycle) {
            i = 0;
            return refresh(area);
        }
        return true;
    }
    
}