package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.powder.Powder;
import cz.hartrik.sg2.world.element.temperature.Burnable;
import cz.hartrik.sg2.world.element.temperature.ThermalConductive;
import cz.hartrik.sg2.world.element.temperature.ThermalConductiveDef;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluenced;

/**
 * 
 * 
 * @version 2014-12-26
 * @author Patrik Harag
 * @param <E>
 */
public class GluedPowderTCF
        <E extends Powder & ThermalInfluenced & Burnable & ThermalConductiveDef>
           extends GluedPowderF<E> implements ThermalConductive {

    private static final long serialVersionUID = 83715083867368_02_078L;
    
    public GluedPowderTCF(E element) {
        super(element);
    }

    // Element
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        getBasicElement().affectNear(x, y, tools, world);
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return getBasicElement().getTemperature() > NORMAL_TEMP;
    }
    
    // ThermalConductive
    
    @Override
    public int conductTemperature(int temperature) {
        return getBasicElement().conductTemperature(temperature);
    }

    @Override
    public void setTemperature(int temperature) {
        getBasicElement().setTemperature(temperature);
    }

    @Override
    public int getTemperature() {
        return getBasicElement().getTemperature();
    }
    
}