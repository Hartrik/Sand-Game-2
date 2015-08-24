package cz.hartrik.sg2.brush;

import cz.hartrik.common.random.RandomSuppliers;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.type.Sourceable;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

/**
 * Speciální štětec pro jediný element.
 * 
 * @version 2014-12-22
 * @author Patrik Harag
 */
public class BrushSingleton extends ABrushBase
        implements CollectibleBrush, SourceableBrush {
    
    private final Element element;

    public BrushSingleton(BrushInfo brushInfo, Element element) {
        super(brushInfo);
        this.element = element;
    }

    @Override
    public Element getElement(Element current) {
        return element;
    }

    @Override
    public boolean isProducer(Element element) {
        return element == element;
    }
    
    // CollectibleBrush
    
    @Override
    public Collection<Element> getElements() {
        return Collections.singletonList(element);
    }

    // SourceableBrush

    @Override
    public Supplier<Sourceable> getSourceSupplier() {
        return RandomSuppliers.of(Sourceable.filter(element));
    }
    
}