
package cz.hartrik.sg2.world.element.gas;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.sg2.world.element.temperature.BurnableDef;
import cz.hartrik.sg2.world.element.temperature.FireSettings;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluencedDef;

/**
 * Element představující hořlavý plyn.
 * 
 * @version 2014-05-20
 * @author Patrik Harag
 */
public class GasF extends SimpleGasElement
        implements BurnableDef, ThermalInfluencedDef {
    
    private static final long serialVersionUID = 83715083867368_02_043L;

    protected final FireSettings fireSettings;

    public GasF(Color color, int density, Chance chanceToMoveUp,
            FireSettings fireSettings) {
        
        super(color, density, chanceToMoveUp);
        this.fireSettings = fireSettings;
    }

    // BurnableDef
    
    @Override
    public FireSettings getFireSettings() {
        return fireSettings;
    }
    
}