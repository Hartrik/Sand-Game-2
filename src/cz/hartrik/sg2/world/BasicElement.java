
package cz.hartrik.sg2.world;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.solid.ClassicWall;
import cz.hartrik.sg2.world.element.solid.Wall;

/**
 * Obsahuje ty nejzákladnější elementy, které se příliš nemnění, používají se
 * při testování atd.
 * 
 * @version 2014-03-11
 * @author Patrik Harag
 */
public class BasicElement {
    
    public static final Air VOID  = new Air(Color.BLACK);
    public static final Air AIR   = new Air(Color.WHITE);
    public static final Wall WALL = new ClassicWall(Color.DARK_GRAY);
    
}