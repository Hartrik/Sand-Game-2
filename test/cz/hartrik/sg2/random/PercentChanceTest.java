package cz.hartrik.sg2.random;

import org.junit.Test;

import static org.junit.Assert.fail;

/**
 *
 * @version 2016-06-27
 * @author Patrik Harag
 */
public class PercentChanceTest {

    @Test(expected = IllegalArgumentException.class)
    public void outOfRangeBelow() {
        Chance chance = new PercentChance(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void outOfRangeAbove() {
        Chance chance = new PercentChance(101);
    }

    @Test
    public void alwaysFalse() {
        Chance alwaysFalse = new PercentChance(0);  // 0 %

        for (int i = 0; i < 1_000; i++)
            if (alwaysFalse.nextBoolean())
                fail();
    }

    @Test
    public void alwaysTrue() {
        Chance alwaysFalse = new PercentChance(100);  // 100 %

        for (int i = 0; i < 1_000; i++)
            if (alwaysFalse.nextBoolean() == false)
                fail();
    }

}
