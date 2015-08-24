
package cz.hartrik.sg2.world.element.temperature;

import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;

/**
 * Rozhraní s <code>default</code> metodou pro snadnou implementaci.
 * 
 * @version 2014-05-16
 * @author Patrik Harag
 */
public interface ThermalInfluencedDef extends ThermalInfluenced, Burnable {

    /**
     * Jednoduché spálení elementu. Element bude nahrazen vzduchem.
     * 
     * @return došlo ke spálení elementu
     */
    @Override
    public default boolean temperature(int x, int y, Tools tools, World world,
            int degrees, boolean fire) {
        
        if (fire && degrees > getDegreeOfFlammability()
                && getChanceToBurn()) {
            world.setAndChange(x, y, world.getBackground());
            return true;
        } else
            return false;
    }
    
}