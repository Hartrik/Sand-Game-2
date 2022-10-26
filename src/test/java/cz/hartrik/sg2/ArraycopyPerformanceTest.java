package cz.hartrik.sg2;

import org.junit.Ignore;
import org.junit.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Testuje, jak dlouho přibližně trvá kopírování pole metodou
 * {@link System#arraycopy}. Zkouší různé typy polí. <p>
 *
 * Inicializace pole není součástí testu.
 *
 * @version 2016-06-27
 * @author Patrik Harag
 */
@Ignore("Toto není unit test")
public class ArraycopyPerformanceTest {

    private static final int SIZE = 800 * 500;  // běžná velikost plátna
    private static final int CYCLES = 10_000;

    // INT

    private static final int[] INT_SOURCE = IntStream.range(0, SIZE).toArray();
    private static final int[] INT_DESTINATION = new int[SIZE];

    @Test
    public void testIntArray() {
        for (int i = 0; i < CYCLES; i++)
            System.arraycopy(INT_SOURCE, 0, INT_DESTINATION, 0, SIZE);
    }

    // FLOAT

    private static final float[] FLOAT_SOURCE = new float[SIZE];
    static {
        IntStream.range(0, SIZE).forEach(i -> FLOAT_SOURCE[i] = (float) i);
    }

    private static final float[] FLOAT_DESTINATION = new float[SIZE];

    @Test
    public void testFloatArray() {
        for (int i = 0; i < CYCLES; i++)
            System.arraycopy(FLOAT_SOURCE, 0, FLOAT_DESTINATION, 0, SIZE);
    }

    // OBJ

    private static final Object[] OBJ_SOURCE = IntStream.range(0, SIZE)
            .mapToObj(i -> new Object())
            .toArray();

    private static final Object[] OBJ_DESTINATION = new Object[SIZE];

    @Test
    public void testObjectArray() {
        for (int i = 0; i < CYCLES; i++)
            System.arraycopy(OBJ_SOURCE, 0, OBJ_DESTINATION, 0, SIZE);
    }

    // LONG

    private static final long[] LONG_SOURCE = LongStream.range(0, SIZE).toArray();
    private static final long[] LONG_DESTINATION = new long[SIZE];

    @Test
    public void testLongArray() {
        for (int i = 0; i < CYCLES; i++)
            System.arraycopy(LONG_SOURCE, 0, LONG_DESTINATION, 0, SIZE);
    }

}