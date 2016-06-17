
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.FallingElement;
import cz.hartrik.sg2.world.element.special.Filterable;

/**
 * Abstraktní třída pro kapaliny.
 *
 * @see FluidDense
 * @see FluidWater
 *
 * @version 2016-06-15
 * @author Patrik Harag
 */
public abstract class Fluid extends FallingElement implements Filterable {

    private static final long serialVersionUID = 83715083867368_02_012L;

    public Fluid(Color color, int density) {
        super(color, density);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        flow(x, y, tools, world);
    }

    protected boolean flow(int x, int y, Tools tools, World world) {
        if (tools.getGravityTools().moveIfValid(x, y, this, x, y + 1))
            return true;

        int direction = (tools.randomBoolean() ? -1 : 1);
        return flow(x, y, tools, world, direction);
    }

    protected abstract boolean flow(
            int x, int y, Tools tools, World world, int direction);

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getGravityTools().testMoveAndValid(this, x, (y + 1))
            || tools.getGravityTools().testMoveAndValid(this, x + 1, y)
            || tools.getGravityTools().testMoveAndValid(this, x - 1, y);
    }

    // Filterable

    @Override
    public boolean testFilter() {
        return Chance.SOMETIMES.nextBoolean();
    }

    @Override
    public Filterable filter() {
        return this;
    }

}