package cz.hartrik.common.io;

import cz.hartrik.common.Checker;
import java.util.function.Supplier;

/**
 * 
 * @version 2015-04-04
 * @author Patrik Harag
 * @param <T> typ výstupu
 */
class ResourceHolder<T> implements Supplier<T> {

    private final Supplier<T> supplier;
    private T data;
    
    public ResourceHolder(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public synchronized T get() {
        if (data == null)
            data = Checker.requireNonNull(supplier.get());
        
        return data;
    }
    
}