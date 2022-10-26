
package cz.hartrik.common;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Immutable dvojice objektů.
 * 
 * @version 2015-03-17
 * @author Patrik Harag
 * @param <T> typ prvního členu
 * @param <U> typ druhého členu
 */
public interface Pair<T, U> {

    /**
     * Vrátí první objekt.
     * 
     * @return první objekt
     */
    public T getFirst();

    /**
     * Vrátí druhý objekt.
     * 
     * @return druhý objekt
     */
    public U getSecond();
    
    /**
     * Změní první člen vytvořením nového páru.
     * 
     * @param <V> nový typ prvního členu
     * @param first nová hodnota prvního členu
     * @return nový pár
     */
    public <V> Pair<V, U> changeFirst(V first);
    
    /**
     * Změní druhý člen vytvořením nového páru.
     * 
     * @param <V> nový typ druhého členu
     * @param second nová hodnota druhého členu
     * @return nový pár
     */
    public <V> Pair<T, V> changeSecond(V second);
    
    /**
     * Vrátí nový pár s pohozenými členy.
     * 
     * @return pár s prohozenými členy
     */
    public Pair<U, T> swap();
    
    // funkcionální
    
    /**
     * Aplikuje funkci na první člen, z výsledku této funkce a druhého členu
     * opět vytvoří pár.
     * 
     * @param <V> nový typ prvního členu
     * @param function funkce aplikovaná na první člen
     * @return nový pár
     */
    public <V> Pair<V, U> mapFirst(Function<T, V> function);
    
    /**
     * Aplikuje funkci na druhý člen, z původního prvního členu a výsledku této
     * funkce opět vytvoří pár.
     * 
     * @param <V> nový typ druhého členu
     * @param function funkce aplikovaná na druhý člen
     * @return nový pár
     */
    public <V> Pair<T, V> mapSecond(Function<U, V> function);
    
    /**
     * Zavolá funkci se členy jako argumenty a výsledek vrátí.
     * 
     * @param <R> typ výstupu
     * @param function funkce
     * @return výstup
     */
    public <R> R reduce(BiFunction<T, U, R> function);
    
    // --- statické metody ---
    
    public static <T, U> Pair<T, U> of(T first, U second) {
        return new SimplePair<>(first, second);
    }
    
    public static <T, U> Pair<T, U> ofNonNull(T first, U second) {
        return new SimplePair<>(
                Checker.requireNonNull(first),
                Checker.requireNonNull(second));
    }
    
    public static <T, U> Pair<T, U> requireNonNull(Pair<T, U> pair) {
        Checker.requireNonNull(pair.getFirst());
        Checker.requireNonNull(pair.getSecond());
        return pair;
    }
    
}