package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.temperature.BurnableDef;
import cz.hartrik.sg2.world.element.temperature.FireSettings;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluencedDef;
import cz.hartrik.sg2.world.element.type.Organic;

/**
 * Element představující dřevo.
 *
 * @version 2014-09-04
 * @author Patrik Harag
 */
public class Wood extends SolidElement
        implements Organic, ThermalInfluencedDef, BurnableDef {

    private static final long serialVersionUID = 83715083867368_02_037L;

    private final Color color;
    protected final FireSettings fireSettings;

    public Wood(Color color, FireSettings fireSettings) {
        this.color = color;
        this.fireSettings = fireSettings;
    }

    // Element
    
    @Override
    public final Color getColor() {
        return color;
    }

    // BurnableDef
    
    @Override
    public FireSettings getFireSettings() {
        return fireSettings;
    }

}