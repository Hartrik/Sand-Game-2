
package cz.hartrik.sg2.world.element.fluid.simplewater;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.BasicElement;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.fluid.EWater;
import cz.hartrik.sg2.world.element.fluid.FluidWater;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluenced;

/**
 * Element představující vodu.
 * 
 * @version 2014-05-01
 * @author Patrik Harag
 */
public class SimpleWater extends FluidWater implements EWater, ThermalInfluenced {
    
    private static final long serialVersionUID = 83715083867368_02_034L;
    public static final Air[] DEFAULT = { BasicElement.AIR };
    
    protected final Chance chance;
    protected Element[] vaporized = DEFAULT;
    
    public SimpleWater(Color color, int density, Chance chance) {
        super(color, density);
        this.chance = chance;
    }

    public void setVaporized(Element... vaporized) {
        this.vaporized = vaporized;
    }
    
    // ThermalInfluenced
    
    @Override
    public boolean temperature(int x, int y, Tools tools, World world,
            int degrees, boolean fire) {
        
        if (degrees > 100 && chance.nextBoolean()) {
            world.setAndChange(x, y, vaporized[tools.randomInt(vaporized.length)]);
            return true;
        }
        return false;
    }

    // Dryable
    
    @Override
    public boolean dry(int x, int y, Tools tools, World world) {
        world.setAndChange(x, y, world.getBackground());
        return true;
    }
    
}