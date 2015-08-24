package cz.hartrik.sg2.tool;

import cz.hartrik.common.Point;
import cz.hartrik.sg2.world.PointConsumer;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Tvar představující přímku.
 * 
 * @version 2015-04-04
 * @author Patrik Harag
 */
public class Line extends AbstractShape {
    
    protected final int x1, y1;
    protected final int x2, y2;

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1; this.y1 = y1;
        this.x2 = x2; this.y2 = y2;
    }

    @Override
    public Iterator<Point> iterator(int x, int y) {
        return createIterator(x1 + x, y1 + y, x2 + x, y2 + y);
    }

    @Override
    public Iterator<Point> iterator(int x, int y, int width, int height) {
        return createIterator(x, y, x + width, y + height);
    }
    
    private Iterator<Point> createIterator(int x1, int y1, int x2, int y2) {
        Stream.Builder<Point> builder = Stream.builder();
        lineAlgorithm(x1, y1, x2, y2,
                (px, py) -> builder.accept(Point.of(px, py)));
        
        return builder.build().iterator();
    }
    
    protected void lineAlgorithm(int x1, int y1, int x2, int y2,
            PointConsumer consumer) {

        consumer.accept(x1, y1);
        
        if ((x1 != x2) || (y1 != y2)) {
            int dx = Math.abs(x2 - x1);
            int dy = Math.abs(y2 - y1);
            int diff = dx - dy;

            int moveX, moveY;

            if (x1 < x2) moveX = 1; else moveX = -1;
            if (y1 < y2) moveY = 1; else moveY = -1;

            while ((x1 != x2) || (y1 != y2)) {  
                int p = 2 * diff;

                if (p > -dy) {
                    diff = diff - dy;
                    x1 = x1 + moveX;
                }
                if (p < dx) {
                    diff = diff + dx;
                    y1 = y1 + moveY;
                }
                consumer.accept(x1, y1);
            }
        }
    }
    
}