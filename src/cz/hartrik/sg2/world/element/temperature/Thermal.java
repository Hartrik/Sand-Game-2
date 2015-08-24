package cz.hartrik.sg2.world.element.temperature;

import cz.hartrik.sg2.world.Element;

/**
 * Rozhraní pro elementy, které mohou vydávat teplo.
 *
 * @version 2014-03-30
 * @author Patrik Harag
 */
public interface Thermal extends Element {

    public static final int NORMAL_TEMP = 20;
    
    /**
     * Vrátí teplotu elmentu v °C.
     *
     * @return templota
     */
    public int getTemperature();

}
