
package cz.hartrik.sg2.world.factory;

import cz.hartrik.sg2.world.Element;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * Rozhraní pro továrnu na elementy.
 * 
 * 
 * @version 2015-01-09
 * @author Patrik Harag
 * @param <E> element
 */
public interface IElementFactory<E extends Element> extends Serializable {

    public default Collection<E> getElements() {
        return Collections.emptyList();
    }
    
    public default void clear() {}
    
}