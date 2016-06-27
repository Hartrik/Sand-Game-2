
package cz.hartrik.sg2.random;

import cz.hartrik.common.reflect.LibraryClass;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Poskytuje statické metody pro vytváření {@link RandomSupplier}, který vybírá
 * náhodné prvky z pole nebo kolekce.
 *
 * @version 2014-09-12
 * @author Patrik Harag
 */
@LibraryClass
public final class RandomSuppliers {

    private RandomSuppliers() {}

    // - array

    @SafeVarargs
    public static <T> RandomSupplier<T> of(T... array) {
        return of(XORShiftRandom.RANDOM, array);
    }

    public static <T> RandomSupplier<T> of(XORShiftRandom random, T[] array) {
        final int arrayLength = array.length;

        if (arrayLength == 1) {
            final T singleton = array[0];
            return () -> singleton;
        } else if (arrayLength == 0) {
            return () -> null;
        } else {
            return () -> array[random.nextInt(arrayLength)];
        }
    }

    // - Collection

    public static <T> RandomSupplier<T> of(Collection<T> collection) {
        return of(XORShiftRandom.RANDOM, collection);
    }

    public static <T> RandomSupplier<T> of(XORShiftRandom random,
            Collection<T> collection) {

        final List<T> list = new ArrayList<>(collection);
        final int size = list.size();

        if (size == 1) {
            final T singleton = list.get(0);
            return () -> singleton;
        } else if (size == 0) {
            return () -> null;
        } else {
            return () -> list.get(random.nextInt(size));
        }
    }

}