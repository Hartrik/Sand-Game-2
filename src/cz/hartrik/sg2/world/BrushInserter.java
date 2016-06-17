
package cz.hartrik.sg2.world;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Point;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;

/**
 * Spravuje vkládání štětcem do pole elementů.
 *
 * @version 2016-06-13
 * @author Patrik Harag
 * @param <T>
 */
public class BrushInserter<T extends ElementArea> implements Inserter<T> {

    protected final T area;
    protected final Inserter<T> inserter;
    protected final Brush brush;
    protected final Controls controls;

    public BrushInserter(Inserter<T> inserter, Brush brush) {
        this(inserter, brush, null);
    }

    public BrushInserter(Inserter<T> inserter, Brush brush, Controls controls) {
        this.area = inserter.getArea();
        this.inserter = inserter;
        this.brush = Checker.requireNonNull(brush);
        this.controls = controls;

        if (inserter instanceof ElementAreaInserter)
            ((ElementAreaInserter) inserter).setControls(controls);
    }

    // insert

    /**
     * Pokusí se na danou pozici aplikovat element z vybraného štětce.
     *
     * @param x horizontální pozice
     * @param y vertikální pozice
     * @return úspěšnost (tj. pokud projde všemi testy)
     */
    public boolean apply(int x, int y) {
        return apply(x, y, brush);
    }

    /**
     * Aplikuje na danou pozici element z vybraného štětce.
     *
     * @param point pozice
     * @return úspěšnost operace
     */
    public boolean apply(Point point) {
        return apply(point.getX(), point.getY());
    }

    /**
     * Pokusí se na danou pozici vložit element z vybraného štětce.
     *
     * @param x horizontální pozice
     * @param y vertikální pozice
     * @return úspěšnost (tj. pokud projde všemi testy)
     */
    public boolean insert(int x, int y) {
        return insert(x, y, brush);
    }

    /**
     * Vloží na danou pozici element z vybraného štětce.
     *
     * @param point pozice
     * @return úspěšnost operace
     */
    public boolean insert(Point point) {
        return insert(point.getX(), point.getY(), brush);
    }

    @Override
    public boolean insert(int x, int y, Element element) {
        return inserter.insert(x, y, element);
    }

    @Override
    public boolean insert(int x, int y, Brush brush) {
        return inserter.insert(x, y, brush);
    }

    @Override
    public boolean apply(int x, int y, Brush brush) {
        return inserter.apply(x, y, brush);
    }

    @Override
    public T getArea() {
        return area;
    }

    @Override
    public void finalizeInsertion() {
        inserter.finalizeInsertion();
    }

    @Override
    public void appendTest(PointElementPredicate<Element> predicate) {
        inserter.appendTest(predicate);
    }

    @Override
    public BrushInserter<T> with(Brush brush) {
        return with(brush, controls);
    }

    @Override
    public BrushInserter<T> with(Brush brush, Controls controls) {
        return new BrushInserter<>(inserter, brush, controls);
    }

    @Override
    public void setEraseTemperature(boolean eraseTemperature) {
        inserter.setEraseTemperature(eraseTemperature);
    }

}