package cz.hartrik.sg2.world.module;

import cz.hartrik.common.Checker;
import cz.hartrik.sg2.world.ElementArea;
import java.util.function.Consumer;

/**
 * @version 2015-03-07
 * @author Patrik Harag
 * @param <T> typ pole elementů
 */
public class SingleActionCon<T extends ElementArea> implements WorldModule<T> {
    
    private final Consumer<T> consumer;

    public SingleActionCon(Consumer<T> consumer) {
        this.consumer = Checker.requireNonNull(consumer);
    }

    @Override
    public boolean nextCycle(T area) {
        return refresh(area);
    }

    @Override
    public boolean refresh(T area) {
        consumer.accept(area);
        
        return false;
    }
    
}