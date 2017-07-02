package cz.hartrik.sg2.world;

import cz.hartrik.common.Pair;
import cz.hartrik.common.Point;
import java.util.function.Predicate;

/**
 * Predikát upravený pro testování pozice i s elementem.
 * 
 * @version 2015-03-28
 * @author Patrik Harag
 * @param <T> typ elementu
 */
@FunctionalInterface
public interface PointElementPredicate <T extends Element>
        extends Predicate<Pair<T, Point>> {

    @Override
    public default boolean test(Pair<T, Point> pair) {
        final Point point = pair.getSecond();
        return test(pair.getFirst(), point.getX(), point.getY());
    }
    
    public boolean test(T element, int x, int y);

    @Override
    public default PointElementPredicate<T> negate() {
        return (e, x, y) -> !test(e, x, y);
    }
    
}