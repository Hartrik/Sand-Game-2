
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.solid.GluedPowder;
import cz.hartrik.sg2.world.element.type.Organic;

/**
 * Element představující půdu.
 * 
 * @version 2014-12-26
 * @author Patrik Harag
 */
public class Soil extends PowderMid implements Organic {

    private static final long serialVersionUID = 83715083867368_02_027L;

    private final GluedPowder<Soil> glued;
    
    public Soil(Color color, int density) {
        super(color, density);
        this.glued = new GluedPowder<>(this);
    }

    @Override
    public Element glue() {
        return glued;
    }
    
}