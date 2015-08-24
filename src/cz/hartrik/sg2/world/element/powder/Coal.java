
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.XORShiftRandom;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.solid.GluedPowderTCF;
import cz.hartrik.sg2.world.element.temperature.FireSettings;
import cz.hartrik.sg2.world.element.type.Organic;

/**
 * Element představující uhlí.
 * 
 * @version 2014-12-26
 * @author Patrik Harag
 */
public class Coal extends PowderMidTCF implements Organic {
    
    private static final long serialVersionUID = 83715083867368_02_049L;
    
    protected static final XORShiftRandom random = new XORShiftRandom();
    protected static final float CONDUCTIVE_INDEX = 0.18f;
    protected static final int DENSITY = 1280;
    protected static final FireSettings FIRE_SETTINGS = new FireSettings(1000, 0, 2000, 1000);
    
    public Coal(Color color) {
        super(color, DENSITY, FIRE_SETTINGS);
    }
    
    // ThermalConductiveDef

    @Override
    public float getConductiveIndex() {
        return CONDUCTIVE_INDEX;
    }

    // Flammable
    
    @Override
    public boolean getChanceToFlareUp() {
        final int result = 800 - temperature;
        if (result < 2) return true;
        
        return random.nextInt(result) == 0;
    }

    // Glueable
    
    @Override
    public Element glue() {
        return new GluedPowderTCF<>(this);
    }
    
}