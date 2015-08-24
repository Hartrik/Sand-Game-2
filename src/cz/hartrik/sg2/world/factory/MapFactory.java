
package cz.hartrik.sg2.world.factory;

import cz.hartrik.sg2.world.Element;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** 
 * Továrna tvoří elementy na základě jednoho vstupu a ukládá je do mapy.
 * 
 * @version 2014-04-11
 * @author Patrik Harag
 * @param <E> typ produkovaného elementu
 * @param <I> vstup, podle kterého se elementy tvoří
 */
public abstract class MapFactory<I, E extends Element>
        implements ISingleInputFactory<I, E> {
    
    private static final long serialVersionUID = 83715083867368_10_002L;

    protected final Map<I, E> elements = new HashMap<>();
    
    @Override
    public E apply(I input) {
        E element = elements.get(input);
        if (element != null) return element;
        
        element = createInstance(input);
        elements.put(input, element);
        return element;
    }

    @Override
    public Collection<E> getElements() {
        return elements.values();
    }
    
    public Set<I> getKeys() {
        return elements.keySet();
    }

    @Override
    public void clear() {
        elements.clear();
    }
    
    protected abstract E createInstance(I input);
    
}