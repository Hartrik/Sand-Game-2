
package cz.hartrik.sg2.app.module.script;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Pair;
import cz.hartrik.common.Point;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.tool.Rectangle;
import cz.hartrik.sg2.tool.Shape;
import cz.hartrik.sg2.world.*;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * @since 2.01
 * @version 2016-07-03
 * @author Patrik Harag
 */
public class Canvas implements Region {

    private final World world;
    private final Controls controls;

    public Canvas(World world, Controls controls) {
        this.world = Checker.requireNonNull(world);
        this.controls = controls;
    }

    // rozměry

    public int getHeight() { return world.getHeight(); }
    public int getWidth()  { return world.getWidth(); }

    public boolean valid(int x, int y) {
        return world.valid(x, y);
    }

    // manipulace s elementy

    public Element get(int x, int y) {
        if (valid(x, y))
            return world.get(x, y);
        else
            throw new IndexOutOfBoundsException(x + " × " + y);
    }

    // since 2.02
    public Element get(Point point) {
        return get(point.getX(), point.getY());
    }

    // since 2.03
    public float getTemperature(int x, int y) {
        if (valid(x, y))
            return world.getTemperature(x, y);
        else
            throw new IndexOutOfBoundsException(x + " × " + y);
    }

    // since 2.03
    public float getTemperature(Point point) {
        return getTemperature(point.getX(), point.getY());
    }

    public void set(int x, int y, Element element) {
        if (valid(x, y))
            world.setAndChange(x, y, Checker.requireNonNull(element));
        else
            throw new IndexOutOfBoundsException(x + " × " + y);
    }

    // since 2.02
    public void set(Point point, Element element) {
        set(point.getX(), point.getY(), element);
    }

    public void set(int x, int y, Brush brush) {
        Element previous = get(x, y);
        Element element = brush.getElement(previous, x, y, world, controls);

        if (element != null)
            world.setAndChange(x, y, element);
    }

    // since 2.02
    public void set(Point point, Brush brush) {
        set(point.getX(), point.getY(), brush);
    }

    // since 2.03
    public void setTemperature(int x, int y, float temperature) {
        if (valid(x, y)) {
            world.setTemperature(x, y, temperature);
            world.getChunkAt(x, y).change();
        } else
            throw new IndexOutOfBoundsException(x + " × " + y);
    }

    // since 2.03
    public void setTemperature(Point point, float temperature) {
        setTemperature(point.getX(), point.getY(), temperature);
    }

    // since 2.02
    public Clip take(Shape shape) {
        return take(shape, 0, 0);
    }

    // since 2.02
    public Clip take(Shape shape, int x, int y) {
        return new Clip(world, shape, x, y);
    }

    // iterátory atd.

    @Override
    public void forEach(Consumer<? super Element> consumer) {
        world.forEach(consumer);
    }

    @Override
    public void forEach(PointElementConsumer<? super Element> consumer) {
        world.forEach(Checker.requireNonNull(consumer));
    }

    @Override
    public void forEachPoint(PointConsumer consumer) {
        world.forEachPoint(Checker.requireNonNull(consumer));
    }

    @Override
    public Stream<Element> stream() {
        return world.stream();
    }

    @Override
    public Stream<Point> streamPoint() {
        return world.streamPoint();
    }

    @Override
    public Stream<Pair<Element, Point>> streamLabeled() {
        return world.streamLabeled();
    }

    @Override
    public Iterator<Element> iterator() {
        return world.iterator();
    }

    @Override
    public Iterator<Pair<Element, Point>> iteratorLabeled() {
        return world.iteratorLabeled();
    }

    @Override
    public Iterator<Point> iteratorPoint() {
        return world.iteratorPoint();
    }

    // since 2.03
    public Stream<Region> streamLines() {
        return world.streamLines();
    }

    // since 2.03
    public Stream<Region> streamLinesReversed() {
        return world.streamLinesReversed();
    }

    // ostatní

    public final Element getBackground() {
        return world.getBackground();
    }

    @Override
    public RegionTools getTools() {
        return world.take(new Rectangle(getWidth(), getHeight()), 0, 0).getTools();
    }

    public Inserter<? extends World> getInserter() {
        return world.getInserter();
    }

}