
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.random.Chance;
import cz.hartrik.sg2.random.XORShiftRandom;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.powder.Salt;

/**
 * Element představující slanou vodu.
 *
 * @version 2016-06-27
 * @author Patrik Harag
 */
public class SaltWater extends Water {

    private static final long serialVersionUID = 83715083867368_02_035L;

    private final XORShiftRandom random = new XORShiftRandom();

    public SaltWater(Color color, int density, Chance chance) {
        super(color, density, chance);
    }

    // Dryable

    @Override
    public Element dry() {
        Element element = random.randomElement(vaporized);

        if (element instanceof Salt)
            return element;
        else
            return null;
    }

}