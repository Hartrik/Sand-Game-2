
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.solid.GluedPowder;

/**
 * Element představující kámen/štěrk.
 *
 * @version 2016-06-15
 * @author Patrik Harag
 */
public class Stone extends PowderMid {

    private static final long serialVersionUID = 83715083867368_02_031L;

    private final GluedPowder<Stone> glued;

    public Stone(Color color, int density) {
        super(color, density);
        this.glued = new GluedPowder<>(this);
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.testTemperature(x, y)
                || super.testAction(x, y, tools, world);
    }

    @Override
    public boolean hasTemperature() {
        return true;
    }

    @Override
    public float loss() {
        return 0.0005f;
    }

    @Override
    public boolean isConductive() {
        return true;
    }

    @Override
    public float getConductiveIndex() {
        return 0.1f;
    }

    // Glueable

    @Override
    public Element glue() {
        return glued;
    }

}