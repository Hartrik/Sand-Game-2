
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.element.solid.Wall;
import cz.hartrik.sg2.world.element.type.Glueable;
import cz.hartrik.sg2.world.element.type.Metamorphic;

/**
 * Element představující rozbitou zeď.
 * 
 * @version 2014-09-11
 * @author Patrik Harag
 * @param <E> typ zdi
 */
public class WallPowder<E extends Wall> extends PowderMid
        implements Metamorphic<E>, Glueable {
    
    private static final long serialVersionUID = 83715083867368_02_067L;
    
    private final E wall;
    
    public WallPowder(E wall, Color color, int density) {
        super(color, density);
        this.wall = wall;
    }

    // Metamorphic
    
    @Override
    public E getBasicElement() {
        return wall;
    }
    
    // Glueable
    
    @Override
    public E glue() {
        return wall;
    }
    
}