
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.element.temperature.FireSettings;
import cz.hartrik.sg2.world.element.Organic;

/**
 * Element představující uhlí.
 *
 * @version 2016-06-14
 * @author Patrik Harag
 */
public class Coal extends BurnablePowder implements Organic {

    private static final long serialVersionUID = 83715083867368_02_049L;

    protected static final float CONDUCTIVE_INDEX = 0.30f;
    protected static final int DENSITY = 1280;
    protected static final FireSettings FIRE_SETTINGS
            = new FireSettings(50, 800, 2000, 1000);

    public Coal(Color color) {
        super(color, DENSITY, FIRE_SETTINGS);
    }

    @Override
    public float getConductiveIndex() {
        return CONDUCTIVE_INDEX;
    }

    @Override
    public float loss() {
        return 0.00001f;
    }

}