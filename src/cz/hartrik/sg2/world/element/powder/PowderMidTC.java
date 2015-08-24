
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.temperature.ThermalConductiveDef;

/**
 * Abstraktní třída pro elementy práškového typu, schopné šířit teplo.
 * 
 * @version 2014-05-22
 * @author Patrik Harag
 */
public abstract class PowderMidTC extends PowderMid
        implements ThermalConductiveDef {
    
    private static final long serialVersionUID = 83715083867368_02_061L;

    protected int temperature;
    
    public PowderMidTC(Color color, int density) {
        this(color, density, NORMAL_TEMP);
    }
    
    public PowderMidTC(Color color, int density, int temperature) {
        super(color, density);
        this.temperature = temperature;
    }

    // Element
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        affectNear(x, y, tools, world);
        super.doAction(x, y, tools, world);
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return getTemperature() > NORMAL_TEMP
                || super.testAction(x, y, tools, world);
    }
    
    // ThermalChangeable
    
    @Override
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }
    
}