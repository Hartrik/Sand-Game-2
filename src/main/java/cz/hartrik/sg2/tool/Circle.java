package cz.hartrik.sg2.tool;

import cz.hartrik.common.Point;
import cz.hartrik.sg2.world.BrushInserter;
import cz.hartrik.sg2.world.PointConsumer;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Třída představuje vyplněný kruh, který se umísťuje podle pomyslného levého
 * horního rohu.
 * <p>
 * Iterátor prochází body následovně:
 * <p>
 * <img src="http://i.imgur.com/iLnUgR1.gif" />
 * 
 * @version 2015-03-19
 * @author Patrik Harag
 */
public class Circle extends AbstractShape {

    private final int radius;

    public Circle(int radius) {
        this.radius = radius;
    }

    @Override
    public void apply(int x, int y, BrushInserter<?> inserter) {
        final int cX = x + radius;
        final int cY = y + radius;
        circleAlgorithm(cX, cY, radius, inserter::apply);
    }
    
    @Override
    public Iterator<Point> iterator(int x, int y) {
        return iterator(x, y, radius);
    }

    @Override
    public Iterator<Point> iterator(int x, int y, int width, int height) {
        int diameter = Math.min(width, height);
        return iterator(x, y, diameter/2);
    }
    
    private Iterator<Point> iterator(int x, int y, int r) {
        Stream.Builder<Point> builder = Stream.builder();
        circleAlgorithm(x, y, r,
                (px, py) -> builder.accept(Point.of(r + px, r + py)));
        
        return builder.build().iterator();
    }
    
    public int getRadius() {
        return radius;
    }
    
    // podle http://stackoverflow.com/a/27452048
    protected void circleAlgorithm(int x, int y, int radius,
            PointConsumer consumer) {
        
        final float sinus = 0.70710678118f;

        final int range = (int) (radius / (2 * sinus));
        for (int i = radius; i >= range; --i) {
            final int j = (int) Math.sqrt(radius * radius - i * i);
            for (int k = -j; k <= j; k++) {
                consumer.accept(x - k, y + i);
                consumer.accept(x - k, y - i);
                consumer.accept(x + i, y + k);
                consumer.accept(x - i, y - k);
            }
        }
        
        final int length = (int) (radius * sinus);
        for (int i = x - length + 1; i < x + length; i++)
            for (int j = y - length + 1; j < y + length; j++)
                consumer.accept(i, j);
    }
    
}