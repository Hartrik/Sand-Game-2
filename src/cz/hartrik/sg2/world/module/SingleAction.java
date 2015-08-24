package cz.hartrik.sg2.world.module;

import cz.hartrik.common.Checker;
import cz.hartrik.sg2.world.ElementArea;

/**
 * @version 2015-03-07
 * @author Patrik Harag
 * @param <T> typ pole elementů
 */
public class SingleAction<T extends ElementArea> implements WorldModule<T> {
    
    private final Runnable runnable;

    public SingleAction(Runnable runnable) {
        this.runnable = Checker.requireNonNull(runnable);
    }

    @Override
    public boolean nextCycle(T area) {
        return refresh(area);
    }

    @Override
    public boolean refresh(T area) {
        runnable.run();
        
        return false;
    }
    
}