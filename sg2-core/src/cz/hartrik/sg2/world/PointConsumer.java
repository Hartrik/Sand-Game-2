package cz.hartrik.sg2.world;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Point;
import java.util.function.Consumer;

/**
 * {@link Consumer} upravený pro přebírání souřadnic.
 * 
 * @version 2014-12-20
 * @author Patrik Harag
 */
@FunctionalInterface
public interface PointConsumer extends Consumer<Point> {

    @Override
    public default void accept(Point point) {
        accept(point.getX(), point.getY());
    }
    
    public void accept(int x, int y);

    public default PointConsumer andThen(PointConsumer after) {
        Checker.requireNonNull(after);
        return (x, y) -> { accept(x, y); after.accept(x, y); };
    }

}