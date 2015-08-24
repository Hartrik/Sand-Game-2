package cz.hartrik.sg2.world;

import cz.hartrik.common.Point;
import java.util.function.Function;

/**
 * Funkce upravená pro pohodlné přebírání souřadnic.
 * 
 * @version 2015-03-16
 * @author Patrik Harag
 * @param <R> typ výstupu
 */
public interface PointFunction<R> extends Function<Point, R> {

    @Override
    public default R apply(Point point) {
        return apply(point.getX(), point.getY());
    }
    
    public R apply(int x, int y);

}
