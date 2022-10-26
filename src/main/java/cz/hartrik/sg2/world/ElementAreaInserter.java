package cz.hartrik.sg2.world;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;

/**
 * Zprostředkuje vkládání elementů do {@link ElementArea}.
 *
 * @version 2016-06-13
 * @author Patrik Harag
 * @param <T> typ pole elementů
 */
public class ElementAreaInserter<T extends ElementArea>
        implements Inserter<T> {

    protected final T area;
    protected boolean eraseTemperature = true;
    protected PointElementPredicate<Element> predicate;

    protected Controls controls;

    public ElementAreaInserter(T elementArea) {
        this.area = elementArea;
        this.predicate = (e, x, y) -> e != null && area.valid(x, y);
    }

    private boolean insertElement(int x, int y, Element element) {
        if (predicate.test(element, x, y)) {
            area.set(x, y, element);
            return true;
        }
        return false;
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
            if (eraseTemperature)
                area.setTemperature(x, y, ElementArea.DEFAULT_TEMPERATURE);

            area.set(x, y, element);
            return true;
        }
        return false;
    }

    @Override
    public boolean insert(int x, int y, Brush brush) {
        if (area.valid(x, y)) {
            float oldTemperature = area.getTemperature(x, y);

            if (eraseTemperature)
                area.setTemperature(x, y, ElementArea.DEFAULT_TEMPERATURE);

            // štětec může měnit také teplotu
            Element element = (brush.isAdvanced())
                    ? brush.getElement(null, x, y, area, controls)
                    : brush.getElement(null);

            if (!insertElement(x, y, element)) {
                // vložení se nezdařilo, vrátíme starou teplotu
                area.setTemperature(x, y, oldTemperature);
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(int x, int y, Brush brush) {
        if (area.valid(x, y)) {
            float oldTemperature = area.getTemperature(x, y);

            if (eraseTemperature)
                area.setTemperature(x, y, ElementArea.DEFAULT_TEMPERATURE);

            // štětec může měnit také teplotu
            Element oldElem = area.get(x, y);
            Element element = (brush.isAdvanced())
                    ? brush.getElement(oldElem, x, y, area, controls)
                    : brush.getElement(oldElem);

            if (!insertElement(x, y, element)) {
                // vložení se nezdařilo, vrátíme starou teplotu
                area.setTemperature(x, y, oldTemperature);
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void appendTest(PointElementPredicate<Element> predicate) {
        PointElementPredicate<Element> current = this.predicate;

        this.predicate = (x, y, e) -> {
            return current.test(x, y, e) && predicate.test(x, y, e);
        };
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

    @Override
    public void setEraseTemperature(boolean eraseTemperature) {
        this.eraseTemperature = eraseTemperature;
    }

    public void setControls(Controls controls) {
        this.controls = controls;
    }

}
