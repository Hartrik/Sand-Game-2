
package cz.hartrik.sg2;

import cz.hartrik.sg2.random.XORShiftRandom;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Dokazuje, že použití switche namísto malého pole je mnohem rychlejší.
 *
 * @version 2016-06-27
 * @author Patrik Harag
 */
@Ignore("Toto není unit test")
public class ArrayVsSwitchPerformanceTest {

    private static final int CYCLES = 1_000_000_000;

    // 4 položky

    static enum Direction { UP, DOWN, LEFT, RIGHT }

    private static final Direction[] DIRECTIONS = Direction.values();
    private static final int LENGTH = DIRECTIONS.length;

    private static int seed;

    @BeforeClass
    public static void before() {
        // stejný seed pro oba testy
        seed = (int) System.nanoTime();
    }

    @Test
    public void randomDirectionUsingArray() {
        final XORShiftRandom random = new XORShiftRandom(seed);

        for (int c = 0; c < CYCLES; c++)
            randomDirection_array(random);
    }

    @Test
    public void randomDirectionUsingSwitch() {
        final XORShiftRandom random = new XORShiftRandom(seed);

        for (int c = 0; c < CYCLES; c++)
            randomDirection_switch(random);
    }

    private static Direction randomDirection_array(XORShiftRandom random) {
        return DIRECTIONS[random.nextInt(LENGTH)];
    }

    private static Direction randomDirection_switch(XORShiftRandom random) {
        switch (random.nextInt(LENGTH)) {
            case 0:  return Direction.UP;
            case 1:  return Direction.DOWN;
            case 2:  return Direction.RIGHT;
            default: return Direction.LEFT;
        }
    }

}