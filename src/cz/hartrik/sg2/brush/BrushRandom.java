
package cz.hartrik.sg2.brush;

import cz.hartrik.common.Checker;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.random.RandomSuppliers;
import cz.hartrik.sg2.random.XORShiftRandom;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.element.special.Sourceable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Jednoduchý "štětec", náhodně vybírající z jednoho či více elementů.
 * Pro elementy bez pevné textury, pro které není potřeba na každý výskyt
 * vytvořit novou instanci.
 *
 * @version 2016-06-16
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
    public Element getElement(Element current, int x, int y,
            ElementArea area, Controls controls) {

        if (current != null && produces(current)) {
            // zabraňuje překreslování elementů nanesených stejným štětcem
            // vrácení objektu != null resetuje teplotu
            return current;
        }

        return getElement();
    }

    @Override
    public Element getElement(Element current) {
        return elements.length > 1
                ? elements[random.nextInt(elements.length)]
                : (elements.length == 0 ? null : elements[0]);
    }

    @Override
    public boolean produces(Element element) {
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

    @Override
    public boolean isAdvanced() {
        return true;
    }

    // CollectibleBrush

    @Override
    public List<Element> getElements() {
        return Arrays.asList(elements);
    }

    // SourceableBrush

    @Override
    public Supplier<Sourceable> getSourceSupplier() {
        return RandomSuppliers.of(Sourceable.filter(getElements()));
    }

}