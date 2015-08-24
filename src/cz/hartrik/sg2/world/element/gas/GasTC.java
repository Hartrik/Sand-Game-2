
package cz.hartrik.sg2.world.element.gas;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.temperature.ThermalConductiveDef;

/**
 * Plyn přenášející teplo.
 * 
 * @version 2014-09-10
 * @author Patrik Harag
 */
public abstract class GasTC extends SimpleGasElement
        implements ThermalConductiveDef {
    
    private static final long serialVersionUID = 83715083867368_02_074L;

    private int temperature;
    
    public GasTC(Color color, int density, Chance chanceToMoveUp) {
        this(color, density, chanceToMoveUp, NORMAL_TEMP);
    }
    
    public GasTC(Color color, int density, Chance chanceToMoveUp, int temperature) {
        super(color, density, chanceToMoveUp);
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