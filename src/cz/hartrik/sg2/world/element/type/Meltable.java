
package cz.hartrik.sg2.world.element.type;

import cz.hartrik.sg2.world.Element;

/**
 * Rozhraní pro elementy, které je možné roztavit. Rozhraní také implementují
 * elementy v roztavené podobě.
 * 
 * @version 2014-05-21
 * @author Patrik Harag
 */
public interface Meltable extends Element {
    
    /**
     * Vrací počet stupňů celsia, po jehož překročení dochází k roztavení
     * elementu a naopak.
     * 
     * @return bod tavení
     */
    public int getMeltingPoint();
    
    /**
     * Slouží k rozpoznání roztaveného stavu.
     * 
     * @return element je roztavený
     */
    public boolean isMolten();
    
}