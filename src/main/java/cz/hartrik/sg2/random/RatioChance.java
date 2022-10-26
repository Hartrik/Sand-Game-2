
package cz.hartrik.sg2.random;

/**
 * Vrací logické hodnoty podle poměrné šance. <p>
 * <b>Platí:</b> <br />
 * <code>poměr == 0</code>, vratí vždy <code>false</code>; <br />
 * <code>poměr == 1</code>, vratí vždy <code>true</code>; <br />
 * <code>poměr == 1000</code> je šance na <code>true</code> zhruba
 *   <code>1:1000</code>, atd...
 *
 * @version 2016-06-27
 * @author Patrik Harag
 */
public final class RatioChance implements Chance {

    private static final long serialVersionUID = 10411599111108_11_003L;

    private final int ratio;
    private final XORShiftRandom random;

    public RatioChance(int ratio) {
        this(new XORShiftRandom(), ratio);
    }

    public RatioChance(XORShiftRandom xorRandom, int ratio) {
        if (ratio < 0)
            throw new IllegalArgumentException("Ratio out of range");

        this.random = xorRandom;
        this.ratio = ratio;
    }

    public int getRatio() {
        return ratio;
    }

    @Override
    public boolean nextBoolean() {
        return (ratio == 1) || (ratio > 1 && (random.nextInt(ratio) == 0));
    }

    // statické metody

    public static RatioChance of(int chance) {
        return of(new XORShiftRandom(), chance);
    }

    public static RatioChance of(XORShiftRandom random, int chance) {
        return new RatioChance(random, chance);
    }

    public static boolean nextBoolean(XORShiftRandom random, int ratio) {
        return (ratio > 0) && ((ratio == 1) || (random.nextInt(ratio) == 0));
    }

}