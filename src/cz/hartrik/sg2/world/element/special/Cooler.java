
package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.temperature.ThermalConductive;

/**
 * Element představující chladné těleso.
 * 
 * @version 2014-09-08
 * @author Patrik Harag
 */
public class Cooler extends SolidElement implements ThermalConductive {
    
    private static final long serialVersionUID = 83715083867368_02_073L;

    private final Color color;
    private final int coolerTemperature;

    public Cooler(Color color, int degrees) {
        this.color = color;
        this.coolerTemperature = degrees;
    }
    
    // Element
    
    @Override
    public Color getColor() {
        return color;
    }

    // ThermalConductive
    
    @Override
    public int getTemperature() {
        return coolerTemperature;
    }
    
    @Override
    public void setTemperature(int temperature) {
        // nic
    }

    @Override
    public int conductTemperature(int temperature) {
        return (temperature <= NORMAL_TEMP)
                ? 0
                : temperature - (temperature - coolerTemperature < NORMAL_TEMP
                    ? temperature - coolerTemperature 
                    : coolerTemperature);
    }
    
}