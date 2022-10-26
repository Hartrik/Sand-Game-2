
package cz.hartrik.sg2.world.factory;

import cz.hartrik.sg2.world.Element;
import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Rozhraní pro tvorbu elementů bez vstupu, podporující lambda výrazy.
 * 
 * @version 2014-08-18
 * @author Patrik Harag
 * @param <E> typ produkovaného elementu
 */
@FunctionalInterface
public interface FactorySupplier <E extends Element>
        extends Supplier<E>, Serializable {
    
}