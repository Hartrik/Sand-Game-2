
package cz.hartrik.sg2.random;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Rozhraní {@link Supplier} umožňující navíc serializaci.
 *
 * @version 2014-09-05
 * @author Patrik Harag
 * @param <T> typ produkovaného elementu
 */
public interface RandomSupplier<T> extends Supplier<T>, Serializable {

}