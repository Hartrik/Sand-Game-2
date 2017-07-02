package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;


/**
 * Element představující uhlík.
 *
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class Carbon extends Wall {

    private static final long serialVersionUID = 83715083867368_02_076L;

    protected static final int DENSITY = 1750;
    protected static final float CONDUCTIVE_INDEX = 0.1f;

    public Carbon(Color color) {
        super(color);
    }

    @Override
    public float getConductiveIndex() {
        return CONDUCTIVE_INDEX;
    }

    @Override
    public float loss() {
        return 0.01f;
    }

    @Override
    public int getDensity() {
        return DENSITY;
    }

}