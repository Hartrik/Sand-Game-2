
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.element.type.Meltable;

/**
 * Abstraktní třída pro kovy.
 * 
 * @version 2014-12-23
 * @author Patrik Harag
 */
public abstract class MetalSolid extends WallTC implements Meltable {
    
    private static final long serialVersionUID = 83715083867368_02_060L;

    public MetalSolid(Color color) {
        super(color);
    }
    
    public MetalSolid(Color color, int temperature) {
        super(color, temperature);
    }

    public void setBaseColor(Color color) {
        this.color = color;
    }

    public Color getBaseColor() {
        return color;
    }
    
    // Meltable
    
    @Override
    public boolean isMolten() {
        return false;
    }
    
}