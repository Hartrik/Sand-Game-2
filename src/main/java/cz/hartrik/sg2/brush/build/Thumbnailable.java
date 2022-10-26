
package cz.hartrik.sg2.brush.build;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.engine.Image;

/**
 * Rozhraní pro štětec, který má nějakou formu náhledu nebo ikony.
 * 
 * @version 2017-08-06
 * @author Patrik Harag
 */
public interface Thumbnailable extends Brush {
    
    /**
     * Vrátí náhled štětce - to může být např. ikona, vzorek elementů... 
     * Vrácený náhled může mít i jiné rozměry, než udávají parametry - ty slouží
     * hlavně u vzorků elementů.
     * 
     * @param width optimální šířka náhledu
     * @param height optimální výška náhledu
     * @return náhled
     */
    Image getThumb(int width, int height);
    
}