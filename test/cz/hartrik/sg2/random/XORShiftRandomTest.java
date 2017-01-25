package cz.hartrik.sg2.random;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 *
 * @version 2016-06-27
 * @author Patrik Harag
 */
public class XORShiftRandomTest {

    // instance

    @Test(expected = IllegalArgumentException.class)
    public void constructor_zeroSeed() {
        XORShiftRandom random = new XORShiftRandom(0);
    }

    @Test
    public void testNextIntBounded_range() {
        XORShiftRandom random = new XORShiftRandom();

        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(10);
            assertTrue("x should be 0 <= x < 10", x >= 0 && x < 10);
        }
    }

    @Test
    public void testNextIntBounded_generatesZeros() {
        XORShiftRandom random = new XORShiftRandom();

        for (int i = 0; i < 10_000_000; i++)
            if (random.nextInt(10) == 0)
                return;

        fail();
    }

    @Test
    public void testNextInt_notGeneratesZeros() {
        XORShiftRandom random = new XORShiftRandom();

        for (int i = 0; i < 10_000_000; i++)
            if (random.nextInt() == 0)
                fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomElement_empty() {
        XORShiftRandom random = new XORShiftRandom();
        random.randomElement(new Object[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomElementInt_empty() {
        XORShiftRandom random = new XORShiftRandom();
        random.randomElement(new int[0]);
    }

    // statické metody

    @Test
    public void testNextRandom_zero() {
        assertThat(XORShiftRandom.nextRandom(0), is(0));
        assertThat(XORShiftRandom.nextRandom(0, 100), is(0));
        assertThat(XORShiftRandom.nextRandom(0L), is(0L));
        assertThat(XORShiftRandom.nextRandom(0L, 100L), is(0L));
    }

}