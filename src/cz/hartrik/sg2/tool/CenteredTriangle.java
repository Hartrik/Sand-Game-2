package cz.hartrik.sg2.tool;

import cz.hartrik.common.Point;
import cz.hartrik.common.Streams;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Třída představující trojúhelník, který se umísťuje podle vypočítaného
 * středu.
 * 
 * @version 2015-03-19
 * @author Patrik Harag
 */
public class CenteredTriangle extends Triangle {

    // konstruktory
    
    public CenteredTriangle(Point a, Point b, Point c) {
        super(a, b, c);
    }

    public CenteredTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        super(x1, y1, x2, y2, x3, y3);
    }

    // metody

    @Override
    public Iterator<Point> iterator(int x, int y) {
        final Point center = countCenter();
        
        final Stream.Builder<Point> builder = Stream.builder();
        triangleAlgorithm(x, y, getA(), getB(), getC(),
                (px, py) -> builder.accept(
                        Point.of(px - center.getX(), py - center.getY())));
        
        return builder.build().iterator();
    }
    
    public Point countCenter() {
        return Streams.stream(getPoints())
                .reduce(Point.of(0, 0), Point::sum)
                .map(i -> i / 3);
    }
    
}