
package cz.hartrik.sg2.brush;

import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.type.Sourceable;
import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Rozhraní s jednoduchou implementací
 * {@link SourceableBrush#getSourceSupplier() getSourceSupplier()}.
 * 
 * @version 2014-09-12
 * @author Patrik Harag
 */
public interface SourceableBrushDef extends SourceableBrush {

    /**
     * Vytvoří dodavatele, který bere elementy pomocí metody
     * {@link Brush#getElement(Element) getElement(element)}. Pokud některý
     * element neimplementuje rozhraní {@link Sourceable}, může dodavatel vrátit
     * <code>null</code>.
     */
    @Override
    public default Supplier<Sourceable> getSourceSupplier() {
        return (Supplier<Sourceable> & Serializable)() -> {
            final Element element = getElement();
            return (element instanceof Sourceable) ? (Sourceable) element : null;
        };
    }
    
}