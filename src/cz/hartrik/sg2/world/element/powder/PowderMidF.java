
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.solid.GluedPowderF;
import cz.hartrik.sg2.world.element.temperature.BurnableDef;
import cz.hartrik.sg2.world.element.temperature.FireSettings;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluencedDef;

/**
 * Práškový element, který hoří a spaluje se.
 * 
 * @version 2014-12-26
 * @author Patrik Harag
 */
public class PowderMidF extends PowderMid
        implements ThermalInfluencedDef, BurnableDef {
    
    private static final long serialVersionUID = 83715083867368_02_044L;

    private final FireSettings fireSettings;

    public PowderMidF(Color color, int density, FireSettings fireSettings) {
        super(color, density);
        this.fireSettings = fireSettings;
    }

    // BurnableDef
    
    @Override
    public FireSettings getFireSettings() {
        return fireSettings;
    }

    // Glueable
    
    @Override
    public Element glue() {
        return new GluedPowderF<>(this);
    }

}