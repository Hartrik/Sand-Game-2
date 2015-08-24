package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.element.SolidElement;
import java.util.function.Supplier;

/**
 * Abstraktní třída pro portály.
 * 
 * @version 2015-02-15
 * @author Patrik Harag
 */
public abstract class Portal extends SolidElement {

    private static final long serialVersionUID = 83715083867368_02_087L;
    
    private final Supplier<Color> supplier;

    public Portal(Supplier<Color> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Color getColor() {
        return supplier.get();
    }

}