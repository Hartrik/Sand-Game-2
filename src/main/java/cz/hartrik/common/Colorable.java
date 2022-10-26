
package cz.hartrik.common;

/**
 * Rozhraní pro objekt, který má nějakou {@link Color barvu}, která mu může být
 * změněna.
 * 
 * @version 2014-06-28
 * @author Patrik Harag
 */
public interface Colorable extends Colored {
    
    /**
     * Nastaví barvu
     * 
     * @param color barva
     */
    public void setColor(Color color);
    
}