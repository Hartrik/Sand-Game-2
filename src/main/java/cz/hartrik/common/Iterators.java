package cz.hartrik.common;

import cz.hartrik.common.reflect.LibraryClass;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * @version 2015-07-25
 * @author Patrik Harag
 */
@LibraryClass
public final class Iterators {

    private Iterators() { }

    public static <T, R> Iterator<R> map(
            Iterator<T> iterator, Function<T, R> function) {

        return new Iterator<R>() {

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public R next() {
                return function.apply(iterator.next());
            }
        };
    }

    public static <T> Iterator<T> iterator(T[] array) {
        return new Iterator<T>() {
            private int i;
            @Override public boolean hasNext() { return i < array.length; }
            @Override public T next()          { return array[i++]; }
        };
    }

    public static <T> Iterator<T> emptyIterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public T next() {
                throw new NoSuchElementException();
            }
        };
    }

    public static <T> Iterable<T> wrap(Iterator<T> iterator) {
        return () -> iterator;
    }

}