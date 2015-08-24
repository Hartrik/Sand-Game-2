
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.solid.GluedPowder;

/**
 * Element představující písek.
 * 
 * @version 2014-12-26
 * @author Patrik Harag
 */
public class Sand extends PowderLow {
    
    private static final long serialVersionUID = 83715083867368_02_023L;

    private final GluedPowder<Sand> glued;
    
    public Sand(Color color, int density) {
        super(color, density);
        this.glued = new GluedPowder<>(this);
    }

    @Override
    public Element glue() {
        return glued;
    }
    
}