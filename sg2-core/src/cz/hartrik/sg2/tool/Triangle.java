package cz.hartrik.sg2.tool;

import cz.hartrik.common.Point;
import cz.hartrik.sg2.world.PointConsumer;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Třída představuje trojúhelník.
 * 
 * @version 2015-03-28
 * @author Patrik Harag
 */
public class Triangle extends AbstractShape implements Draggable {
    
    private final Point a;
    private final Point b;
    private final Point c;

    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        this(Point.of(x1, y1), Point.of(x2, y2), Point.of(x3, y3));
    }
    
    public Triangle(Point a, Point b, Point c) {
        final Point[] points = trim(a, b, c);
        
        this.a = points[0];
        this.b = points[1];
        this.c = points[2];
    }
    
    @Override
    public Iterator<Point> iterator(int x, int y) {
        return iterator(x, y, a, b, c);
    }

    @Override
    public Iterator<Point> iterator(int x, int y, int width, int height) {
        Point t = Point.of(0, height);
        Point u = Point.of(width, height);
        Point v = Point.of(width / 2, 0);
        
        //   V
        //
        // T   U
        
        return iterator(x, y, t, u, v);
    }
    
    private Iterator<Point> iterator(int x, int y, Point a, Point b, Point c) {
        Stream.Builder<Point> builder = Stream.builder();
        triangleAlgorithm(x, y, a, b, c,
                (px, py) -> builder.accept(Point.of(px, py)));
        
        return builder.build().iterator();
    }
    
    // http://www.sunshine2k.de/coding/java/TriangleRasterization/TriangleRasterization.html
    protected void triangleAlgorithm(
            int mx, int my, Point a, Point b, Point c, PointConsumer consumer) {
        
        final int maxX = Math.max(a.getX(), Math.max(b.getX(), c.getX()));
        final int minX = Math.min(a.getX(), Math.min(b.getX(), c.getX()));
        final int maxY = Math.max(a.getY(), Math.max(b.getY(), c.getY()));
        final int minY = Math.min(a.getY(), Math.min(b.getY(), c.getY()));
        
        final Point v1 = new Point(b.getX() - a.getX(), b.getY() - a.getY());
        final Point v2 = new Point(c.getX() - a.getX(), c.getY() - a.getY());
        
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                Point q = new Point(x - a.getX(), y - a.getY());
                        
                float s = (float) crossProduct(q, v2) / crossProduct(v1, v2);
                float t = (float) crossProduct(v1, q) / crossProduct(v1, v2);
                
                if ( (s >= 0) && (t >= 0) && (s + t <= 1))
                    consumer.accept(mx + x, my + y);
            }
        }
    }
    
    private int crossProduct(Point v1, Point v2) {
        return (v1.getX() * v2.getY() - v1.getY() * v2.getX());
    }

    // gettery

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public Point getC() {
        return c;
    }
    
    public Point[] getPoints() {
        return new Point[] { a, b, c };
    }
    
    // statické pomocné metody
    
    public static Point[] trim(Point... points) {
        int minX = Stream.of(points).mapToInt(Point::getX).min().getAsInt();
        int minY = Stream.of(points).mapToInt(Point::getY).min().getAsInt();
        
        if (minX != 0) {
            points = Stream.of(points)
                    .map(p -> p.mapX(x -> x - minX))
                    .toArray(Point[]::new);
        }
        
        if (minY != 0) {
            points = Stream.of(points)
                    .map(p -> p.mapY(y -> y - minY))
                    .toArray(Point[]::new);
        }
        
        return points;
    }
    
    public static Point[] countPoints(double a, double b, double c,
            Function<Double, Integer> round) {
        
        Point A = Point.of(0, 0);
        Point B = Point.of(round.apply(c), 0);
        
        // úhel alpha
        // cos alpha = (b² + c² - a²) / 2ab
        double angleRad = Math.acos((b*b + c*c - a*a) / (2*a*b));

        // výpočet pozice
        double x = b * Math.cos(angleRad);
        double y = b * Math.sin(angleRad);

        // -y aby byl vrcholem nahoru
        Point C = Point.of(round.apply(x), round.apply(-y));
        
        return new Point[] { A, B, C };
    }
    
}