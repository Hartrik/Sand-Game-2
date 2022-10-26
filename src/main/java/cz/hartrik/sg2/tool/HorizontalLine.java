package cz.hartrik.sg2.tool;

import cz.hartrik.common.Point;
import java.util.Iterator;

/**
 * Jednodušší typ přímky, vhodný pro iteraci po řádcích.
 *
 * @version 2015-11-20
 * @author Patrik Harag
 */
public class HorizontalLine extends Line {

    public HorizontalLine(int x, int y, int width) {
        super(x, y, x + width, y);
    }

    @Override
    public Iterator<Point> iterator(int x, int y) {
        return createIterator(x1 + x, y1 + y, x2 + x, y2 + y);
    }

    @Override
    public Iterator<Point> iterator(int x, int y, int width, int height) {
        return createIterator(x, y, x + width, y);
    }

    private Iterator<Point> createIterator(int x1, int y1, int x2, int y2) {
        // y1 a y2 jsou vždy stejné

        return new Iterator<Point>() {
            int i = x1;

            @Override
            public boolean hasNext() {
                return i <= x2;
            }

            @Override
            public Point next() {
                return new Point(i++, y1);
            }
        };
    }

}