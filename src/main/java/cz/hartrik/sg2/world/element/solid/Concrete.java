
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;

/**
 * Element představující beton. Vzniká smícháním a ztvrdnutím
 * {@link cz.hartrik.sg2.world.element.Water} a {@link Cement}.
 *
 * @version 2016-06-13
 * @author Patrik Harag
 */
public class Concrete extends Wall {

    private static final long serialVersionUID = 83715083867368_02_007L;

    public Concrete(Color color) {
        super(color);
    }

    @Override
    public float getConductiveIndex() {
        return 0.1f;
    }

}