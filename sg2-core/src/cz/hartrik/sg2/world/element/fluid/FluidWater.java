
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.GravityTools;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.gas.Gas;

/**
 * Abstraktní třída pro kapaliny s hustotou podobné vodě.
 *
 * @version 2014-09-05
 * @author Patrik Harag
 */
public abstract class FluidWater extends Fluid {

    private static final long serialVersionUID = 83715083867368_02_056L;

    public FluidWater(Color color, int density) {
        super(color, density);
    }

    @Override // rychlost především...
    protected boolean flow(final int x, final int y, Tools tools,
            World world, final int direction) {

        GravityTools gTools = tools.getGravityTools();

        final int x1 = x + direction;
        if (!world.valid(x1, y)) return false;
        final Element first = world.get(x1, y);

        if (first instanceof Air || first instanceof Fluid || first instanceof Gas) {
            final int x2 = x1 + direction;
            if (world.valid(x2, y)) {
                final Element second = world.get(x2, y);
                if (second instanceof Air || second instanceof Fluid) {
                    final int x3 = x2 + direction;

                    return (world.valid(x3, y)
                            && gTools.move(x, y, this, x3, y, world.get(x3, y)))
                                || (gTools.move(x, y, this, x2, y, second)
                                    || gTools.move(x, y, this, x1, y, first));
                } else
                    return gTools.move(x, y, this, x1, y, first);
            } else
                return gTools.move(x, y, this, x1, y, first);
        }

        return false;
    }

}