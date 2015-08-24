
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;

/**
 * @version 2014-09-10
 * @author Patrik Harag
 */
public abstract class BoilingFluid extends FluidWaterTC {
    
    private static final long serialVersionUID = 83715083867368_02_079L;

    private final int boilingPoint;
    
    public BoilingFluid(Color color, int density, int boilingPoint) {
        this(color, density, boilingPoint, NORMAL_TEMP);
    }

    public BoilingFluid(Color color, int density, int boilingPoint,
            int temperature) {
        
        super(color, density, temperature);
        this.boilingPoint = boilingPoint;
    }

    // Element
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (getTemperature() > boilingPoint) {
            affectNear(x, y, tools, world);

            // nejdřív dolů
            if (tools.getGravityTools().moveIfValid(x, y, this, x, y + 1))
                return;

            // potom dostran
            if (!flow(x, y, tools.getGravityTools(), world,
                    (tools.randomBoolean() ? -1 : 1))) {
                // povyskočení nahoru
                tools.getGravityTools().moveIfValid(x, y, this, x, y - 1);
            }
        } else {
            super.doAction(x, y, tools, world);
        }
    }
    
}