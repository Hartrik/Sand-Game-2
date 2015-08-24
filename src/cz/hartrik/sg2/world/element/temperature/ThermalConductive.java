
package cz.hartrik.sg2.world.element.temperature;

/**
 * Rozhraní pro elementy, které mohou vést teplo.
 * 
 * @version 2014-05-11
 * @author Patrik Harag
 */
public interface ThermalConductive extends ThermalChangeable {
    
    /**
     * Převede nějaké teplo na element.
     * 
     * @param temperature působící teplota v °C
     * @return množství převzatého tepla v °C
     */
    public int conductTemperature(int temperature);
    
}