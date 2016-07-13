
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.World;

/**
 * Kapalina přenášející teplo, která se pohybuje jako voda a při dosažení
 * bodu varu "poskakuje".
 *
 * @version 2016-06-15
 * @author Patrik Harag
 */
public abstract class BoilingFluid extends FluidWater {

    private static final long serialVersionUID = 83715083867368_02_079L;

    private final int boilingPoint;

    public BoilingFluid(Color color, int density, int boilingPoint) {
        super(color, density);
        this.boilingPoint = boilingPoint;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (world.getTemperature(x, y) > boilingPoint) {

            // dolů
            if (tools.getGravityTools().moveIfValid(x, y, this, x, y + 1))
                return;

            // dostran
            if (flow(x, y, tools, world, (tools.randomBoolean() ? -1 : 1)))
                return;

            // povyskočení nahoru
            tools.getGravityTools().moveIfValid(x, y, this, x, y - 1);

        } else {
            super.doAction(x, y, tools, world);
        }
    }

    @Override
    public boolean hasTemperature() {
        return true;
    }

    @Override
    public boolean isConductive() {
        return true;
    }

    @Override
    public abstract float getConductiveIndex();

    @Override
    public abstract float loss();

}