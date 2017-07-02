
package cz.hartrik.sg2.random;

/**
 * Vrací logické hodnoty podle procentní šance. Čím větší šance, tím větší
 * pravděpodobnost, že vrátí <code>true</code>.
 *
 * @version 2014-03-15
 * @author Patrik Harag
 */
public class PercentChance implements Chance {

    private static final long serialVersionUID = 10411599111108_11_002L;

    private final XORShiftRandom random;
    private final int percent;

    public PercentChance(int percent) {
        this(new XORShiftRandom(), percent);
    }

    public PercentChance(XORShiftRandom xorRandom, int percent) {
        if (percent > 100 || percent < 0)
            throw new IllegalArgumentException("Percent out of range");

        this.random = xorRandom;
        this.percent = percent;
    }

    @Override
    public boolean nextBoolean() {
        return (percent == 100)
                || (percent > 0 && (random.nextInt(100) < percent));
    }

}