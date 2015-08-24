package cz.hartrik.sg2.world;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;
import java.util.function.Predicate;

/**
 * @version 2015-03-28
 * @author Patrik Harag
 */
class RegionToolsImpl implements RegionTools {

    private final Region region;
    private final ElementArea area;
    
    public RegionToolsImpl(Region region, ElementArea area) {
        this.region = region;
        this.area = area;
    }

    // fill
    
    @Override
    public void fill(Element element) {
        Inserter<?> inserter = area.getInserter();
        region.forEachPoint((x, y) -> inserter.insert(x, y, element));
        inserter.finalizeInsertion();
    }

    @Override
    public void fill(Brush brush, Controls controls) {
        BrushInserter<?> inserter = area.getInserter().with(brush, controls);
        region.forEachPoint(inserter::apply);
        inserter.finalizeInsertion();
    }

    // replace
    
    @Override
    public void replace(Brush brush1, Brush brush2) {
        Inserter<?> inserter = area.getInserter();
        region.forEach((oldElement, x, y) -> {
            if (brush1.isProducer(oldElement))
                inserter.apply(x, y, brush2);
        });
        inserter.finalizeInsertion();
    }

    @Override
    public void replaceAll(Predicate<Element> predicate, Brush brush) {
        Inserter<?> inserter = area.getInserter();
        region.forEach((element, x, y) -> {
            if (predicate.test(element))
                inserter.apply(x, y, brush);
        });
        inserter.finalizeInsertion();
    }

    @Override
    public void replaceAll(PointElementPredicate<Element> predicate, Brush brush) {
        BrushInserter<?> inserter = area.getInserter().with(brush);
        region.forEach((element, x, y) -> {
            if (predicate.test(element, x, y))
                inserter.apply(x, y);
        });
        inserter.finalizeInsertion();
    }
    
}
