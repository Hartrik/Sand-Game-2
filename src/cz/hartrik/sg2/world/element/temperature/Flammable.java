
package cz.hartrik.sg2.world.element.temperature;

import cz.hartrik.sg2.world.Element;

/**
 * Rozhraní pro všechny hořlavé elementy.
 * 
 * @version 2014-05-16
 * @author Patrik Harag
 */
public interface Flammable extends Element {
    
    /**
     * Vrátí číso, které označuje žár produkovaný pořením elementu.
     * 
     * @return maximální žár při hoření elementu ve °C.
     */
    public int getFlammableNumber();
    
    /**
     * Vrací stupeň celsia, na kterém už element vzplane.
     * 
     * @see #getChanceToFlareUp()
     * @return stupně celsia potřebné k vzplanutí
     */
    public int getDegreeOfFlammability();
    
    /**
     * Vrací <code>boolean</code> podle šance na vzplanutí při dosažení
     * potřebné teploty.
     * 
     * @see #getDegreeOfFlammability()
     * @return šance na vzplanutí
     */
    public boolean getChanceToFlareUp();
    
}