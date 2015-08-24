
package cz.hartrik.sg2.brush;

import cz.hartrik.sg2.world.element.type.Sourceable;
import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Rozhraní pro štětce, které podporují tvorbu zdrojů.
 * 
 * @version 2014-08-21
 * @author Patrik Harag
 */
public interface SourceableBrush extends Brush {
    
    /**
     * Vrátí dodavatele, který slouží k vytvoření zdroje.
     * 
     * @return dodavatel elementů implementující rozhraní {@link Sourceable}
     *         a {@link Serializable}
     */
    public Supplier<Sourceable> getSourceSupplier();
    
}