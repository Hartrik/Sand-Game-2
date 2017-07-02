
package cz.hartrik.sg2.tool;

import cz.hartrik.common.Point;
import cz.hartrik.sg2.world.BrushInserter;

/**
 * Rozhraní pro tvar štětce.
 * 
 * @version 2015-03-21
 * @author Patrik Harag
 */
public interface Tool {
    
    public static final Tool EMPTY_TOOL = (x, y, inserter) -> {};
    
    /**
     * Aplikuje nástroj na určitou pozici.
     * 
     * @param mX horizontální pozice myši
     * @param mY vertikální pozice myši
     * @param inserter inserter
     */
    public void apply(
            int mX, int mY, BrushInserter<?> inserter);
    
    /**
     * Aplikuje nástroj na určitou pozici.
     * 
     * @param point pozice
     * @param inserter inserter
     */
    public default void apply(
            Point point, BrushInserter<?> inserter) {
        
        apply(point.getX(), point.getY(), inserter);
    }
    
}