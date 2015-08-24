
package cz.hartrik.sg2.tool;

import cz.hartrik.common.Point;
import cz.hartrik.common.reflect.TODO;
import cz.hartrik.sg2.world.BrushInserter;

/**
 * Rozhraní pro nástroj, který může být aplikován tahem.
 * Implementace tohoto rozhraní je nezbytná pro nepřerušované vykreslování
 * a podporu přímky.
 * 
 * @version 2015-04-04
 * @author Patrik Harag
 */
public interface Draggable extends Tool {
    
    /**
     * Vykreslí tah.
     * 
     * @param x1 první horizontální pozice
     * @param y1 první vertikální pozice
     * @param x2 druhá horizontální pozice
     * @param y2 druhá vertikální pozice
     * @param inserter inserter
     */
    public default void stroke(
            int x1, int y1, int x2, int y2, BrushInserter<?> inserter) {
        
        Draggable.simpleStroke(x1, y1, x2, y2, this, inserter);
    }
    
    /**
     * Vykreslí tah.
     * 
     * @param p1 počáteční pozice
     * @param p2 konečná pozice
     * @param inserter inserter
     */
    public default void stroke(Point p1, Point p2, BrushInserter<?> inserter) {
        stroke(p1.getX(), p1.getY(), p2.getX(), p2.getY(), inserter);
    }
    
    // pomocné statické metody
    
    @TODO("Lepší alogitmus pro tah")
    static void simpleStroke(int x1, int y1, int x2, int y2,
            Tool tool, BrushInserter<?> inserter) {

        new Line(0, 0, 0, 0)  // tento způsob nevytváří instance Point
                .lineAlgorithm(x1, y1, x2, y2, (x, y) -> tool.apply(x, y, inserter));
    }
    
}