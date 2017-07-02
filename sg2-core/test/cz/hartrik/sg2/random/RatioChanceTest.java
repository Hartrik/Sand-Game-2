package cz.hartrik.sg2.random;

import org.junit.Test;

import static org.junit.Assert.fail;

/**
 *
 * @version 2016-06-27
 * @author Patrik Harag
 */
public class RatioChanceTest {

    @Test(expected = IllegalArgumentException.class)
    public void outOfRangeBelow() {
        Chance chance = new RatioChance(-1);
    }
    
    @Test
    public void alwaysFalse() {
        Chance alwaysFalse = new RatioChance(0);

        for (int i = 0; i < 1_000; i++)
            if (alwaysFalse.nextBoolean())
                fail();
    }

}