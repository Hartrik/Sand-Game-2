package cz.hartrik.common;

import java.util.Objects;

/**
 * Základní implementace {@link Pair}.
 * 
 * @version 2015-03-17
 * @author Patrik Harag
 */
class SimplePair<T, U> extends PairBase<T, U> implements Pair<T, U> {
    
    private static final long serialVersionUID = 10411599111108_10_002L;
    
    private final T first;
    private final U second;

    public SimplePair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    // --- Pair
    
    @Override
    public T getFirst() {
        return first;
    }

    @Override
    public U getSecond() {
        return second;
    }
        
    // --- Object

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        
        final Pair<?, ?> other = (Pair<?, ?>) obj;
        return Objects.equals(first, other.getFirst())
                && Objects.equals(second, other.getSecond());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.first);
        hash = 79 * hash + Objects.hashCode(this.second);
        return hash;
    }
    
}