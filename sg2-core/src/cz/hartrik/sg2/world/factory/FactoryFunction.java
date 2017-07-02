
package cz.hartrik.sg2.world.factory;

import cz.hartrik.sg2.world.Element;
import java.io.Serializable;
import java.util.function.Function;

/**
 * Rozhraní pro tvorbu elementů s jedním vstupem, podporující lambda výrazy.
 * 
 * @version 2014-05-18
 * @author Patrik Harag
 * @param <I> vstup
 * @param <E> produkovaný element
 */
@FunctionalInterface
public interface FactoryFunction <I, E extends Element>
        extends Function<I, E>, Serializable {
    
}