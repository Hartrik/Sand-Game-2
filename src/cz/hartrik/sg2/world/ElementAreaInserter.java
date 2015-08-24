package cz.hartrik.sg2.world;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;

/**
 * Zprostředkuje vkládání elementů do {@link ElementArea}.
 * 
 * @version 2015-03-21
 * @author Patrik Harag
 * @param <T> typ pole elementů
 */
public class ElementAreaInserter<T extends ElementArea>
        implements Inserter<T> {

    protected final T area;
    protected PointElementPredicate<Element> predicate;

    public ElementAreaInserter(T elementArea) {
        this.area = elementArea;
        this.predicate = (e, x, y) -> e != null && area.valid(x, y);
    }

    // Inserter<T>
    
    /**
     * @return úspěšnost vložení
     *         (<code>false</code> pro <code>element == null</code> nebo
     *         nevalidní pozici)
     */
    @Override
    public boolean insert(int x, int y, Element element) {
        if (predicate.test(element, x, y)) {
            area.set(x, y, element);
            
            return true;
        }
        return false;
    }

    @Override
    public boolean insert(int x, int y, Brush brush) {
        if (area.valid(x, y)) {
            return insert(x, y, (brush.isAdvanced())
                    ? brush.getElement(null, x, y, area, null)
                    : brush.getElement(null));
        }
        return false;
    }
    
    @Override
    public boolean apply(int x, int y, Brush brush) {
        if (area.valid(x, y)) {
            
            Element oldElem = area.get(x, y);
            Element newElem = (brush.isAdvanced())
                    ? brush.getElement(oldElem, x, y, area, null)
                    : brush.getElement(oldElem);
            
            return insert(x, y, newElem);
        }
        return false;
    }
    
    @Override
    public void appendTest(PointElementPredicate<Element> predicate) {
        PointElementPredicate<Element> current = this.predicate;
        
        this.predicate = (x, y, e)
                -> current.test(x, y, e) && predicate.test(x, y, e);
    }
    
    @Override
    public void finalizeInsertion() {
        // není potřeba
    }

    @Override
    public T getArea() {
        return area;
    }

    @Override
    public BrushInserter<T> with(Brush brush, Controls controls) {
        return new BrushInserter<>(this, brush, controls);
    }
    
}
