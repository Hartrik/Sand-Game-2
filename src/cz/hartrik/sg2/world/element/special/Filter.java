package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.element.SolidElement;

/**
 * Abstraktní třída pro filtry.
 * 
 * @version 2015-02-15
 * @author Patrik Harag
 */
public abstract class Filter extends SolidElement {
    
    private static final long serialVersionUID = 83715083867368_02_084L;
    
    private final Color color;

    public Filter(Color color) {
        this.color = color;
    }
    
    @Override
    public Color getColor() {
        return color;
    }
    
}