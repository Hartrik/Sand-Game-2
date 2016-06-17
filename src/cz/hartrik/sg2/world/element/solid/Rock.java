
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;

/**
 * Element představující skálu.
 *
 * @version 2016-06-16
 * @author Patrik Harag
 */
public final class Rock extends Wall {

    private static final long serialVersionUID = 83715083867368_02_021L;

    public Rock(Color color) {
        super(color);
    }

    @Override
    public float getConductiveIndex() {
        return 0.08f;
    }

}