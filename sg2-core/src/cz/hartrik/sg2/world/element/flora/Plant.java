
package cz.hartrik.sg2.world.element.flora;

import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.ClassicElement;
import cz.hartrik.sg2.world.element.powder.Soil;
import cz.hartrik.sg2.world.element.Organic;

/**
 * Abstraktní třída pro rostliny.
 * 
 * @version 2014-03-29
 * @author Patrik Harag
 */
public abstract class Plant extends ClassicElement implements Organic {
    
    private static final long serialVersionUID = 83715083867368_02_020L;

    protected static final int SOIL_NOT_FOUND = -1;
    
    protected int findSoil(int x, int y, int maxCycles, World world) {
        for (int i = 1; i < (maxCycles + 1); i++) {
            final int cY = i + y;
            
            if (world.valid(x, cY)) {
                final Element element = world.get(x, cY);
                if (element instanceof Soil)
                    return i;
                else if (!(element instanceof Grass))
                    return SOIL_NOT_FOUND;
            } else {
                return SOIL_NOT_FOUND;
            }
        }
        return SOIL_NOT_FOUND;
    }
    
}