
package cz.hartrik.sg2.world.element.temperature;

/**
 * Rozhraní pro elementy s proměnitelnou teplotou.
 * 
 * @version 2014-05-11
 * @author Patrik Harag
 */
public interface ThermalChangeable extends Thermal {
    
    /**
     * Nastaví teplotu elementu.
     * 
     * @param temperature teplota ve °C
     */
    public void setTemperature(int temperature);
    
}