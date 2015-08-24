
package cz.hartrik.sg2.brush;

import cz.hartrik.sg2.world.Element;
import java.util.Collection;

/**
 * Rozhraní pro štětce, které mohou vrátit kolekci elementů, které produkují.
 * To může posloužit dalším štětcům.
 * 
 * @version 2014-05-07
 * @author Patrik Harag
 */
public interface CollectibleBrush extends Brush {
    
    /**
     * Vrátí kolekci elementů, které štětec produkuje.
     * 
     * @return kolekce elementů
     */
    public Collection<Element> getElements();
    
}