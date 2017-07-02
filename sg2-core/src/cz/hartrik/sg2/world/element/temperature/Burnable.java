
package cz.hartrik.sg2.world.element.temperature;

/**
 * Rozhraní pro elementy, které se působením ohně spalují nebo jinak mění.
 * 
 * @version 2014-05-16
 * @author Patrik Harag
 */
public interface Burnable extends Flammable {
    
    /**
     * Vrací <code>boolean</code> podle šance na spálení nebo jinou změnu
     * elementu.
     * 
     * @see FireSettings#getChanceToBurn()
     * @return šance na spálení
     */
    public boolean getChanceToBurn();
    
}