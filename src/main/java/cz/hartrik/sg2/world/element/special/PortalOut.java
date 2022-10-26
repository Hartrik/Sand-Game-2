package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import java.util.function.Supplier;

/**
 * Element představující výstupní portál.
 * 
 * @version 2015-02-15
 * @author Patrik Harag
 */
public class PortalOut extends Portal {

    private static final long serialVersionUID = 83715083867368_02_089L;
    
    public PortalOut(Supplier<Color> supplier) {
        super(supplier);
    }
    
}