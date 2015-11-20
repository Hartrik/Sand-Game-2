package cz.hartrik.sg2.tool;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Point;
import cz.hartrik.common.Streams;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Třída představující obdélník. Pozicí se určuje levý horní roh.
 *
 * @version 2015-11-20
 * @author Patrik Harag
 */
public class Rectangle extends AbstractShape {

    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        this.width  = Checker.checkArgument(width,  width  > 0);
        this.height = Checker.checkArgument(height, height > 0);
    }

    // AbstractShape

    @Override
    public Iterator<Point> iterator(int x, int y) {
        return new RectangleIterator(x, y, getWidth(), getHeight());
    }

    @Override
    public Iterator<Point> iterator(int x, int y, int width, int height) {
        return new RectangleIterator(x, y, width, height);
    }

    // iterace po řádcích

    private Iterator<Line> iteratorLines(int x, int y) {
        return new Iterator<Line>() {
            private int i = y;
            @Override public boolean hasNext() { return i < y + getHeight(); }
            @Override public Line next() {
                return new HorizontalLine(x, i++, getWidth() - 1);
            }
        };
    }

    public Stream<Line> streamLines(int x, int y) {
        return Streams.stream(iteratorLines(x, y));
    }

    private Iterator<Line> iteratorLinesReversed(int x, int y) {
        return new Iterator<Line>() {
            private int i = getHeight() - 1 - y;
            @Override public boolean hasNext() { return i >= y; }
            @Override public Line next() {
                return new HorizontalLine(x, i--, getWidth() - 1);
            }
        };
    }

    public Stream<Line> streamLinesReversed(int x, int y) {
        return Streams.stream(iteratorLinesReversed(x, y));
    }

    // gettery

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}