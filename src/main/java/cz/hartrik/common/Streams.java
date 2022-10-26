package cz.hartrik.common;

import cz.hartrik.common.reflect.LibraryClass;
import cz.hartrik.common.reflect.TODO;
import cz.hartrik.common.reflect.TaskType;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * @version 2015-07-25
 * @author Patrik Harag
 */
@LibraryClass @TODO(type = TaskType.JAVADOC)
public final class Streams {

    private Streams() { }

    public static IntStream range(int endExclusive) {
        return IntStream.range(0, endExclusive);
    }

    public static IntStream range(int startInclusive, int endExclusive) {
        if (startInclusive == endExclusive)
            return IntStream.empty();

        final boolean reversed = (startInclusive > endExclusive);
        final IntStream stream = IntStream.range(
                reversed ? endExclusive : startInclusive,
                reversed ? startInclusive : endExclusive);

        return (reversed)
                ? stream.map(i -> startInclusive - i)
                : stream;
    }

    public static IntStream range(int startInclusive, int endExclusive, int step) {
        final int diff = endExclusive - startInclusive;

        if (diff > 0) {  // čísla postupně
            if (step == 1) return range(startInclusive, endExclusive);

            return (step < 2)
                    ? IntStream.empty()  // nemožné
                    : createRange(startInclusive, endExclusive, step);

        } else if (diff < 0)  // čísla sestupně
            return (step > -1)
                    ? IntStream.empty()  // nemožné
                    : createRange(startInclusive, endExclusive, step);
        else
            return IntStream.empty();
    }

    private static IntStream createRange(int from, int to, int step) {
        return IntStream
                .iterate(from, i -> i + step)
                .limit((long) Math.abs(Math.ceil(1. * (to - from) / step)));
    }

    public static <A, B> Stream<Pair<A,B>> zip(Stream<A> as, Stream<B> bs) {
        final Iterator<A> i1 = as.iterator();
        final Iterator<B> i2 = bs.iterator();

        final Iterable<Pair<A, B>> iterable = () -> new Iterator<Pair<A, B>>() {
            @Override
            public boolean hasNext() {
                return i1.hasNext() && i2.hasNext();
            }

            @Override
            public Pair<A,B> next() {
                return Pair.of(i1.next(), i2.next());
            }
        };

        return stream(iterable);
    }

    public static <T> Stream<IntPair<T>> indexed(Stream<T> stream) {
        final Counter counter = new Counter();
        return stream.map(item -> new IntPair<>(counter.increase(), item));
    }

    public static <T> Stream<T> stream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static <T> Stream<T> stream(Iterator<T> iterator) {
        return stream(() -> iterator);
    }

    @SafeVarargs
    public static <T> Stream<T> stream(T... array) {
        return Arrays.stream(array);
    }

    public static <T> Iterable<T> iterable(Stream<T> stream) {
        return () -> stream.iterator();
    }

}