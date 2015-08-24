
package cz.hartrik.sg2.world.element.fluid.simplewater;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.gas.TemporaryGas;
import cz.hartrik.sg2.world.element.temperature.Fire;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluenced;

/**
 * Element představující vodní páru.
 * 
 * @version 2014-04-10
 * @author Patrik Harag
 */
public class SimpleSteam extends TemporaryGas<SimpleWater> implements ThermalInfluenced {
    
    private static final long serialVersionUID = 83715083867368_02_047L;

    public SimpleSteam(Color color, int density, Chance chanceToMoveUp,
            SimpleWater element, Chance chanceToUnwrap) {
        
        super(color, density, chanceToMoveUp, element, chanceToUnwrap);
    }

    /**
     * Rychlý průchod přes plameny.
     */
    @Override
    public boolean temperature(int x, int y, Tools tools, World world,
            int degrees, boolean fire) {
        
        if (!fire) return false;
        
        for (int nY = y - 1; nY >= 0; nY--) {
            Element next = world.get(x, nY);
            if (!(next instanceof Fire)) {
                if (next instanceof Air) {
                    world.setAndChange(x, y, next);
                    world.setAndChange(x, nY, this);
                }
                return false;
            }
        }
        return false;
    }
    
}