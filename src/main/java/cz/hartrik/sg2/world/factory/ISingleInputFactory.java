
package cz.hartrik.sg2.world.factory;

import cz.hartrik.sg2.world.Element;
import java.util.function.Function;

/**
 * Rozhraní pro továrnu s jedním vstupem.
 * 
 * @version 2014-05-23
 * @author Patrik Harag
 * @param <E> produkovaný element
 * @param <I> vstup
 */
@FunctionalInterface
public interface ISingleInputFactory<I, E extends Element>
        extends IElementFactory<E>, Function<I, E> {
    
}