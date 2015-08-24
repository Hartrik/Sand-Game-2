
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.solid.GluedPowder;

/**
 * Element představující kámen/štěrk.
 * 
 * @version 2014-12-26
 * @author Patrik Harag
 */
public class Stone extends PowderMid {
    
    private static final long serialVersionUID = 83715083867368_02_031L;
    
    private final GluedPowder<Stone> glued;
    
    public Stone(Color color, int density) {
        super(color, density);
        this.glued = new GluedPowder<>(this);
    }

    @Override
    public Element glue() {
        return glued;
    }

}