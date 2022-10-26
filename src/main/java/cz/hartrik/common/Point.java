package cz.hartrik.common;

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

/**
 * Obvykle slouží k označení polohy. Souřadnice jsou vnitřně uloženy jako
 * <code>int</code>, při použití metod {@link Pair} jsou převáděny na
 * {@link Integer}.
 *
 * @version 2015-03-17
 * @author Patrik Harag
 */
public final class Point extends PairBase<Integer, Integer>
        implements Pair<Integer, Integer> {

    private static final long serialVersionUID = 10411599111108_10_005L;

    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public Point changeX(int x) {
        return new Point(x, y);
    }

    public Point changeY(int y) {
        return new Point(x, y);
    }

    public Point mapX(IntUnaryOperator operator) {
        return Point.of(operator.applyAsInt(x), y);
    }

    public Point mapY(IntUnaryOperator operator) {
        return Point.of(x, operator.applyAsInt(y));
    }

    public Point map(IntUnaryOperator operator) {
        return Point.of(operator.applyAsInt(x), operator.applyAsInt(y));
    }
    
    public int[] toIntArray() {
        return new int[] { x, y };
    }
    
    // Pair
    
    @Override
    public Integer getFirst() {
        return x;
    }

    @Override
    public Integer getSecond() {
        return y;
    }

    @Override
    public Point swap() {
        return new Point(y, x);
    }
    
    // Object
    
    /**
     * Vrátí textovou reprezentaci dvojice ve tvaru
     * <code>[x, y]</code>.
     *
     * @return textová reprezentace dvojice
     */
    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Point)) {
            return false;
        }

        final Point other = (Point) obj;
        return (x == other.x) && (y == other.y);
    }

    @Override
    public int hashCode() {
        return x * 31 + y;
    }

    // --- statické metody
    
    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public static Point reduce(Point p1, Point p2, IntBinaryOperator operator) {
        return Point.of(
                operator.applyAsInt(p1.getX(), p2.getX()),
                operator.applyAsInt(p1.getY(), p2.getY()));
    }

    public static Point sum(Point p1, Point p2) {
        return reduce(p1, p2, Integer::sum);
    }

}
