
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.element.temperature.FireSettings;

/**
 * Práškový element, který hoří a spaluje se.
 *
 * @version 2016-06-14
 * @author Patrik Harag
 */
public class Thermite extends BurnablePowder {

    private static final long serialVersionUID = 83715083867368_02_073L;

    public Thermite(Color color, int density, FireSettings fireSettings) {
        super(color, density, fireSettings);
    }

    @Override
    public float getConductiveIndex() {
        return 0.3f;
    }

    @Override
    public float loss() {
        return 0.001f;
    }

}