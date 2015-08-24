
package cz.hartrik.sg2.brush;

import cz.hartrik.common.Checker;
import cz.hartrik.common.random.RandomSuppliers;
import cz.hartrik.common.random.XORShiftRandom;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.type.Sourceable;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Jedoduchý "štětec", náhodně vybírající z jedoho či více elementů.
 * Pro elementy bez pevné textury, pro které není potřeba na každý výskyt
 * vytvořit novou instanci.
 * 
 * @version 2014-09-12
 * @author Patrik Harag
 */
public class BrushRandom extends ABrushBase
        implements CollectibleBrush, SourceableBrush {

    protected static final XORShiftRandom random = new XORShiftRandom();
    
    protected final Element[] elements;

    public BrushRandom(BrushInfo brushInfo, Element... elements) {
        super(brushInfo);
        this.elements = elements;
    }

    @Override
    public Element getElement(Element current) {
        if (current != null && isProducer(current)) return null;
        
        return elements.length > 1
                ? elements[random.nextInt(elements.length)]
                : elements.length == 0 ? null : elements[0];
    }

    @Override
    public boolean isProducer(Element element) {
        Checker.requireNonNull(element);
        if (elements.length == 0) return false;
        
        if (elements.length > 1) {
            for (Element next : elements)
                if (test(element, next)) return true;
            return false;
        } else
            return test(element, elements[0]);
    }

    private boolean test(Element element, Element next) {
        if (next == element) return true;
        
        // v případě načteného serializovaného objektu
        return next.getClass() == element.getClass()
                && element.getColor().equals(next.getColor())
                && element.getDensity() == next.getDensity();
    }
    
    // CollectibleBrush
    
    @Override
    public Collection<Element> getElements() {
        return Arrays.asList(elements);
    }
    
    // SourceableBrush
    
    @Override
    public Supplier<Sourceable> getSourceSupplier() {
        return RandomSuppliers.of(Sourceable.filter(getElements()));
    }
    
}