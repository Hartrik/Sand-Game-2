
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.sg2.process.GravityTools;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.FallingElement;
import cz.hartrik.sg2.world.element.type.Filterable;

/**
 * Abstraktní třída pro kapaliny.
 * 
 * @see FluidDense
 * @see FluidWaterLike
 * 
 * @version 2015-01-07
 * @author Patrik Harag
 */
public abstract class Fluid extends FallingElement implements Filterable {
    
    private static final long serialVersionUID = 83715083867368_02_012L;

    public Fluid(Color color, int density) {
        super(color, density);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (tools.getGravityTools().moveIfValid(x, y, this, x, y + 1)) return;
        flow(x, y, tools.getGravityTools(), world,
                (tools.randomBoolean() ? -1 : 1));
    }

    protected abstract boolean flow(final int x, final int y, GravityTools tools,
            World world, final int direction);
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getGravityTools().testMoveAndValid(this, x, (y + 1))
            || (world.valid(x + 1, y)
                && tools.getGravityTools().testMove(this, x + 1, y))
            || (world.valid(x - 1, y)
                && tools.getGravityTools().testMove(this, x - 1, y));
    }

    // _Filterable

    @Override
    public boolean filter() {
        return Chance.SOMETIMES.nextBoolean();
    }

    @Override
    public Element onFilter(int x, int y, Tools tools, World world) {
        return world.getBackground();
    }

}