
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.temperature.ThermalConductiveDef;

/**
 * Abstraktní třída pro zdi, které přenášejí teplo.
 * 
 * @version 2014-05-20
 * @author Patrik Harag
 */
public abstract class WallTC extends Wall implements ThermalConductiveDef {
    
    private static final long serialVersionUID = 83715083867368_02_053L;

    protected int temperature;
    
    public WallTC(Color color) {
        this(color, NORMAL_TEMP);
    }
    
    public WallTC(Color color, int temperature) {
        super(color);
        this.temperature = temperature;
    }

    // Element
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        affectNear(x, y, tools, world);
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return getTemperature() > NORMAL_TEMP;
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