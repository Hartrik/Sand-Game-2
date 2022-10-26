package cz.hartrik.sg2.world.element;

import cz.hartrik.common.Color;

/**
 * Element představující vzduch.
 *
 * @version 2014-09-04
 * @author Patrik Harag
 */
public class Air extends NonSolidElement {

    private static final long serialVersionUID = 83715083867368_02_001L;

    private final Color color;

    public Air(Color color) {
        this.color = color;
    }

    // Element
    
    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public final int getDensity() {
        return 0;
    }

}
