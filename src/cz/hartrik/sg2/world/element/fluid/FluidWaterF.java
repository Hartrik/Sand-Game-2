
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.element.temperature.BurnableDef;
import cz.hartrik.sg2.world.element.temperature.FireSettings;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluencedDef;

/**
 * Element představující hořlavou kapalinu.
 * 
 * @version 2014-05-20
 * @author Patrik Harag
 */
public class FluidWaterF extends FluidWater
        implements BurnableDef, ThermalInfluencedDef {
    
    private static final long serialVersionUID = 83715083867368_02_042L;

    private final FireSettings fireSettings;

    public FluidWaterF(Color color, int density, FireSettings fireSettings) {
        super(color, density);
        this.fireSettings = fireSettings;
    }

    // BurnableDef
    
    @Override
    public FireSettings getFireSettings() {
        return fireSettings;
    }
    
}