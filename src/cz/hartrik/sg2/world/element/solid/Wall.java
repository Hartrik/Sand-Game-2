
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.sg2.world.element.powder.WallPowder;
import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.powder.Powder;
import cz.hartrik.sg2.world.element.type.Destructible;

/**
 * Abstraktní element představující stěnu/zeď.
 * 
 * @version 2014-09-03
 * @author Patrik Harag
 */
public abstract class Wall extends SolidElement implements Destructible {
    
    private static final long serialVersionUID = 83715083867368_02_033L;

    protected Color color;
    
    public Wall(Color color) {
        this.color = color;
    }

    // Element
    
    @Override
    public Color getColor() {
        return color;
    }
    
    // Destructible

    @Override
    public Powder destroy() {
        return new WallPowder<>(this, color, getDensity());
    }
    
}