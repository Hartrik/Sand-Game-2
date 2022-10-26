package cz.hartrik.sg2.app.extension.canvas;

import cz.hartrik.common.Point;
import cz.hartrik.common.Streams;

/**
 * Centrovaný kurzor pro polygon.
 * 
 * @version 2015-03-20
 * @author Patrik Harag
 */
public class PolygonCursorCentered extends PolygonCursor {

    public PolygonCursorCentered(CanvasWithCursor canvas, Point... points) {
        super(canvas, points);
    }
    
    @Override
    protected Double[] countPoints(double x, double y, Point[] points) {
        final Point center = countCenter();
        return super.countPoints(x - center.getX(), y - center.getY(), points);
    }
    
    public Point countCenter() {
        return Streams.stream(getPoints())
                .reduce(Point.of(0, 0), Point::sum)
                .map(i -> i / getPoints().length);
    }
    
}