package cz.hartrik.sg2.world;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Pair;
import cz.hartrik.common.Point;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Consumer upravený k tomu, aby různými způsoby přebíral souřadnice i s
 * elementem.
 * 
 * @version 2015-02-17
 * @author Patrik Harag
 * @param <T> typ elementu
 */
@FunctionalInterface
public interface PointElementConsumer <T extends Element>
        extends BiConsumer<T, Point>, Consumer<Pair<T, Point>> {

    // Consumer<Pair<T, Point>>

    @Override
    public default void accept(Pair<T, Point> pair) {
        final Point point = pair.getSecond();
        accept(pair.getFirst(), point.getX(), point.getY());
    }

    // BiConsumer<T, Point>

    @Override
    public default void accept(T element, Point point) {
        accept(element, point.getX(), point.getY());
    }

    // PointElementConsumer <T extends Element>

    public void accept(T element, int x, int y);

    public default PointElementConsumer<T> andThen(PointElementConsumer<T> after) {
        Checker.requireNonNull(after);
        return (e, x, y) -> { accept(e, x, y); after.accept(e, x, y); };
    }

}