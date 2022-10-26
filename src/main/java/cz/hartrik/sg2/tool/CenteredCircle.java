package cz.hartrik.sg2.tool;

import cz.hartrik.common.Point;
import cz.hartrik.sg2.world.BrushInserter;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Třída představuje vyplněný kruh, který se umísťuje podle středu.
 * 
 * @version 2015-03-19
 * @author Patrik Harag
 */
public class CenteredCircle extends Circle {

    public CenteredCircle(int radius) {
        super(radius);
    }

    @Override
    public void apply(int x, int y, BrushInserter<?> inserter) {
        circleAlgorithm(x, y, getRadius(), inserter::apply);
    }
    
    @Override
    public Iterator<Point> iterator(int x, int y) {
        Stream.Builder<Point> builder = Stream.builder();
        circleAlgorithm(x, y, getRadius(),
                (px, py) -> builder.accept(Point.of(px, py)));
        
        return builder.build().iterator();
    }

}