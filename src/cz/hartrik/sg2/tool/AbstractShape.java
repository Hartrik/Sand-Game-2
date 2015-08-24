package cz.hartrik.sg2.tool;

import cz.hartrik.common.Point;
import cz.hartrik.common.Streams;
import cz.hartrik.sg2.world.BrushInserter;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Abstraktní třída pro geometrické tvary.
 * 
 * @version 2015-03-15
 * @author Patrik Harag
 */
public abstract class AbstractShape implements Shape {

    @Override
    public void apply(int mX, int mY, BrushInserter<?> inserter) {
        iterator(mX, mY).forEachRemaining(inserter::apply);
    }

    @Override
    public void apply(
            int mX, int mY, int width, int height, BrushInserter<?> inserter) {
        
        iterator(mX, mY, width, height).forEachRemaining(inserter::apply);
    }

    // iterátory

    @Override
    public abstract Iterator<Point> iterator(int x, int y, int width, int height);
    
    @Override
    public abstract Iterator<Point> iterator(int x, int y);

    @Override
    public Iterator<Point> iterator() {
        return iterator(0, 0);
    }

    // streamy

    @Override
    public Stream<Point> stream(int x, int y, int width, int height) {
        return Streams.stream(iterator(x, y, width, height));
    }
    
    @Override
    public Stream<Point> stream(int x, int y) {
        return Streams.stream(iterator(0, 0));
    }

    @Override
    public Stream<Point> stream() {
        return Streams.stream(iterator());
    }

}