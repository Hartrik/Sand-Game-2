
package cz.hartrik.sg2.random;

import java.io.Serializable;

/**
 * Rozhraní pro třídu, která vrací <code> boolean </code> na základě nějaké
 * pravděpodobnosti.
 *
 * @version 2014-06-19
 * @author Patrik Harag
 */
@FunctionalInterface
public interface Chance extends Serializable {

    // metody

    /**
     * Vrací náhodnou logickou hodnotu.
     *
     * @return boolean
     */
    public boolean nextBoolean();

    // konstanty

    static final XORShiftRandom DEFAULT_RANDOM = XORShiftRandom.RANDOM;

    /** 100% šance na <code>true</code>. */
    public static final Chance ALWAYS = () -> true;

    /**  75% šance na <code>true</code>. */
    public static final Chance USUALLY = new PercentChance(DEFAULT_RANDOM, 75);

    /**  50% šance na <code>true</code>. */
    public static final Chance OFTEN = () -> DEFAULT_RANDOM.nextBoolean();

    /**  25% šance na <code>true</code>. */
    public static final Chance SOMETIMES = new RatioChance(DEFAULT_RANDOM, 4);

    /**   0% šance na <code>true</code>. */
    public static final Chance NEVER = () -> false;

}