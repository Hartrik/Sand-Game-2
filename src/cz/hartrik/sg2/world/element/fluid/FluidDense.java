
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.World;

/**
 * Abstraktní třída pro hustější kapaliny.
 *
 * @version 2014-05-20
 * @author Patrik Harag
 */
public abstract class FluidDense extends Fluid {

    private static final long serialVersionUID = 83715083867368_02_059L;

    public FluidDense(Color color, int density) {
        super(color, density);
    }

    @Override
    protected boolean flow(int x, int y, Tools tools, World world,
            int direction) {

        return tools.getGravityTools().moveIfValid(x, y, this, x + direction, y);
    }
    
}