package cz.hartrik.common;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Implementuje generické metody, které jsou u každého páru stejné.
 * 
 * @version 2015-03-17
 * @author Patrik Harag
 */
abstract class PairBase<T, U> implements Pair<T, U> {

    @Override
    public <V> Pair<V, U> changeFirst(V first) {
        return new SimplePair<>(first, getSecond());
    }

    @Override
    public <V> Pair<T, V> changeSecond(V second) {
        return new SimplePair<>(getFirst(), second);
    }

    @Override
    public Pair<U, T> swap() {
        return new SimplePair<>(getSecond(), getFirst());
    }
    
    @Override
    public <V> Pair<V, U> mapFirst(Function<T, V> function) {
        return new SimplePair<>(function.apply(getFirst()), getSecond());
    }

    @Override
    public <V> Pair<T, V> mapSecond(Function<U, V> function) {
        return new SimplePair<>(getFirst(), function.apply(getSecond()));
    }

    @Override
    public <R> R reduce(BiFunction<T, U, R> function) {
        return function.apply(getFirst(), getSecond());
    }
    
    /**
     * Vrátí textovou reprezentaci dvojice ve tvaru
     * <code>{první, druhý}</code>.
     * 
     * @return textová reprezentace dvojice
     */
    @Override
    public String toString() {
        return "{" + String.valueOf(getFirst()) + ", "
                   + String.valueOf(getSecond()) + "}";
    }
    
}