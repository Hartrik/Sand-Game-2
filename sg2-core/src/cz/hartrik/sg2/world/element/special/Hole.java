
package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.element.SolidElement;

/**
 * Abstraktní třída pro statické elementy, které něco pohlcují.
 * 
 * @version 2014-03-27
 * @author Patrik Harag
 */
public abstract class Hole extends SolidElement {
    
    private static final long serialVersionUID = 83715083867368_02_017L;
    
    private final Color color;

    public Hole(Color color) {
        this.color = color;
    }
    
    @Override
    public Color getColor() {
        return color;
    }
    
}