
package cz.hartrik.sg2.world.element.fluid.simplewater;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;

/**
 * Element představující slanou vodu.
 * Slouží jen k odlišení od obyčejné vody.
 * 
 * @version 2014-05-01
 * @author Patrik Harag
 */
public class SimpleWaterSalt extends SimpleWater implements EWaterSalt {
    
    private static final long serialVersionUID = 83715083867368_02_035L;

    public SimpleWaterSalt(Color color, int density, Chance chance) {
        super(color, density, chance);
    }

    // Dryable
    
    @Override
    public boolean dry(int x, int y, Tools tools, World world) {
        Element element = vaporized[tools.randomInt(vaporized.length)];
        if (!(element instanceof Salt))
            element = world.getBackground();
        
        world.setAndChange(x, y, element);
        return true;
    }
    
}