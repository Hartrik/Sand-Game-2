
package cz.hartrik.sg2.world;

import cz.hartrik.common.Iterators;
import cz.hartrik.common.Pair;
import cz.hartrik.common.Point;
import cz.hartrik.common.Streams;
import cz.hartrik.sg2.tool.Rectangle;
import cz.hartrik.sg2.tool.Shape;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Třída spravující pole elementů. Pro přístup do pole často umožňuje vedle
 * <code>int</code> použít i {@link Point}, protože se často používá ve spojení
 * s {@link Iterator}, {@link Stream} atd...
 *
 * @version 2015-11-20
 * @author Patrik Harag
 */
public class ElementArea implements Region, Serializable {

    private static final long serialVersionUID = 83715083867368_01_001L;

    private final Element[] array;
    private final int width;
    private final int height;

    public ElementArea(int width, int height) {
        this.array = new Element[width * height];
        this.width = width;
        this.height = height;
    }

    public ElementArea(ElementArea elementArea) {
        final int length = elementArea.array.length;

        this.array = new Element[length];
        this.width = elementArea.width;
        this.height = elementArea.height;

        System.arraycopy(elementArea.array, 0, array, 0, length);
    }

    // rozměry...

    public final int getHeight() { return height; }
    public final int getWidth()  { return width; }

    public final boolean valid(final int x, final int y) {
        return (x > -1) && (y > -1) && (x < width) && (y < height);
    }

    public final boolean valid(final Point point) {
        return valid(point.getX(), point.getY());
    }

    // přístupové metody

    public final Element get(final int x, final int y) {
        return array[(y * width) + x];
    }

    public final Element get(Point point) {
        return get(point.getX(), point.getY());
    }

    public final void set(final int x, final int y, final Element element) {
        array[(y * width) + x] = element;
    }

    public final void set(Point point, Element element) {
        set(point.getX(), point.getX(), element);
    }

    public final void setIfNotNull(int x, int y, Element element) {
        if (element != null) set(x, y, element);
    }

    public final void setIfNotNull(Point point, Element element) {
        setIfNotNull(point.getX(), point.getX(), element);
    }

    public final boolean setIfValid(int x, int y, Element element) {
        if (valid(x, y)) {
            set(x, y, element);
            return true;
        } else return false;
    }

    public final boolean setIfValid(Point point, Element element) {
        return setIfValid(point.getX(), point.getX(), element);
    }

    public final Clip take(Shape shape, int x, int y) {
        return new Clip(this, shape, x, y);
    }

    public final Element[] getArray() {
        return array;
    }

    // různé

    /**
     * Metoda je volána processorem před každým cyklem.
     */
    public void nextCycle() { }

    /**
     * Metoda je volána po každém zásahu zvenčí - například po převrácení
     * obsahu, naplnění, kreslení...
     */
    public void refresh() { }

    @Override
    public ElementAreaTools<? extends ElementArea> getTools() {
        return new ElementAreaTools<ElementArea>(this) {
            @Override
            public ElementArea empty(int width, int height) {
                return new ElementArea(width, height);
            }
        };
    }

    public Inserter<? extends ElementArea> getInserter() {
        return new ElementAreaInserter<>(this);
    }

    // iterátory...

    @Override
    public Iterator<Element> iterator() {
        return Iterators.iterator(array);
    }

    @Override
    public Iterator<Point> iteratorPoint() {
        return new Iterator<Point>() {
            private int i;
            @Override public boolean hasNext() { return i < array.length; }
            @Override public Point next() {
                return new Point(i % width, i++ / width);
            }
        };
    }

    @Override
    public Iterator<Pair<Element, Point>> iteratorLabeled() {
        return new Iterator<Pair<Element, Point>>() {
            private int i;
            @Override public boolean hasNext() { return i < array.length; }
            @Override public Pair<Element, Point> next() {
                Element element = array[i];
                return Pair.of(element, Point.of(i % width, i++ / width));
            }
        };
    }

    @Override
    public Stream<Element> stream() {
        return Arrays.stream(array);
    }

    @Override
    public Stream<Point> streamPoint() {
        return Streams.stream(iteratorPoint());
    }

    @Override
    public Stream<Pair<Element, Point>> streamLabeled() {
        return Streams.stream(iteratorLabeled());
    }

    @Override
    public void forEach(Consumer<? super Element> consumer) {
        for (Element array1 : array) {
            consumer.accept(array1);
        }
    }

    @Override
    public void forEach(PointElementConsumer<? super Element> consumer) {
        for (int i = 0; i < array.length; i++)
            consumer.accept(array[i], i % width, i / width);
    }

    @Override
    public void forEachPoint(PointConsumer consumer) {
        for (int i = 0; i < array.length; i++)
            consumer.accept(i % width, i / width);
    }

    public Stream<Region> streamLines() {
        Rectangle rect = new Rectangle(width, height);
        return rect.streamLines(0, 0).map(line -> take(line, 0, 0));
    }

    public Stream<Region> streamLinesReversed() {
        Rectangle rect = new Rectangle(width, height);
        return rect.streamLinesReversed(0, 0).map(line -> take(line, 0, 0));
    }

}