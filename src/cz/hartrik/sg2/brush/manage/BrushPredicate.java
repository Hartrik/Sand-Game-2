
package cz.hartrik.sg2.brush.manage;

import cz.hartrik.sg2.brush.Brush;
import java.util.function.BiPredicate;

/**
 * Predikát sloužící k filtrování štětců.
 *
 * @version 2014-12-31
 * @author Patrik Harag
 * @param <T> typ štětce
 */
@FunctionalInterface
public interface BrushPredicate<T extends Brush> extends BiPredicate<T, Boolean> {

    @Override
    public default boolean test(T t, Boolean u) {
        return accept(t, u);
    }

    public boolean accept(T brush, boolean hidden);

}