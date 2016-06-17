
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;

/**
 * Element představující kamennou zeď.
 *
 * @version 2016-06-13
 * @author Patrik Harag
 */
public final class StoneWall extends Wall {

    private static final long serialVersionUID = 83715083867368_02_032L;

    public StoneWall(Color color) {
        super(color);
    }

    @Override
    public float getConductiveIndex() {
        return 0.1f;
    }

}