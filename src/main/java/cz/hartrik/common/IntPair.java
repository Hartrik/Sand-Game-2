
package cz.hartrik.common;

import java.util.Objects;

/**
 * Dvojice číslo - objekt. Číslo je uchováváno jako <code>int</code>, ne jako
 * {@link Integer}! <code>Integer</code> je vytvořen jen při použití některých
 * zděděných metod.
 * 
 * @version 2015-03-17
 * @author Patrik Harag
 * @param <T> typ druhého členu
 */
public final class IntPair<T> extends PairBase<Integer, T>
        implements Pair<Integer, T> {
    
    private static final long serialVersionUID = 10411599111108_10_004L;

    private final int value;
    private final T object;

    public IntPair(int value, T object) {
        this.value = value;
        this.object = object;
    }

    public int getValue() {
        return value;
    }
    
    public IntPair<T> changeValue(int value) {
        return new IntPair<>(value, object);
    }
    
    // --- Pair
    
    @Override
    public Integer getFirst() {
        return value;
    }

    @Override
    public T getSecond() {
        return object;
    }

    // --- Object
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        
        final IntPair<?> other = (IntPair<?>) obj;
        
        return (this.value == other.value)
                && Objects.equals(this.object, other.object);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.value;
        hash = 67 * hash + Objects.hashCode(this.object);
        return hash;
    }
    
    // --- statické tovární metody
    
    public static <T> IntPair<T> of(int value, T object) {
        return new IntPair<>(value, object);
    }
    
}