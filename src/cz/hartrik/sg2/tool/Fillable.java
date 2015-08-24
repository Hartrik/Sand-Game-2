package cz.hartrik.sg2.tool;

import cz.hartrik.common.Point;
import cz.hartrik.sg2.world.BrushInserter;

/**
 * Rozhraní pro nástroje, které mohou vyplnit / ovlivnit určitou oblast.
 * 
 * @version 2015-03-19
 * @author Patrik Harag
 */
public interface Fillable {
    
    /**
     * Aplikuje nástroj na určitou oblast.
     * 
     * @param mX horizontální pozice myši
     * @param mY vertikální pozice myši
     * @param width šířka
     * @param height výška
     * @param inserter inserter
     */
    public void apply(
            int mX, int mY, int width, int height, BrushInserter<?> inserter);
    
    /**
     * Aplikuje nástroj na určitou oblast.
     * 
     * @param point pozice, na kterou bude nástroj působit
     * @param width šířka
     * @param height výška
     * @param inserter inserter
     */
    public default void apply(
            Point point, int width, int height, BrushInserter<?> inserter) {
        
        apply(point.getX(), point.getY(), width, height, inserter);
    }
    
}