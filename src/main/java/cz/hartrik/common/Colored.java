
package cz.hartrik.common;

/**
 * Rozhraní pro objekt, který má nějakou {@link Color barvu}.
 * 
 * @see Colorable
 * 
 * @version 2014-06-28
 * @author Patrik Harag
 */
public interface Colored {
    
    /**
     * Vrací barvu
     * 
     * @return barva
     */
    public Color getColor();
    
}