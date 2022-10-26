
package cz.hartrik.sg2.random;

import java.io.Serializable;

/**
 * Jednoduchý, ale rychlý generátor pseudonáhodných celých čísel.
 *
 * @version 2016-06-27
 * @author Patrik Harag
 */
public class XORShiftRandom implements Serializable {

    private static final long serialVersionUID = 10411599111108_11_001L;

    public static final XORShiftRandom RANDOM = new XORShiftRandom();

    // --- INSTANCE ---

    private int last;

    /**
     * Vytvoří novou instanci, {@code seed} dodává {@code System.nanoTime()}.
     */
    public XORShiftRandom() {
        this((int) System.nanoTime());
    }

    /**
     * Vytvoří novou instanci.
     *
     * @param seed nenulová počáteční hodnota
     */
    public XORShiftRandom(final int seed) {
        if (seed == 0)
            throw new IllegalArgumentException("Wrong seed");

        this.last = seed;
    }

    /**
     * Vrátí další pseudonáhodné nenulové celé číslo.
     *
     * @return pseudonáhodné celé číslo
     */
    public final int nextInt() {
        last ^= (last << 6);
        last ^= (last >>> 21);
        last ^= (last << 7);

        return last;
    }

    /**
     * Vrátí pseudonáhodné celé číslo v rozmezí 0 (včetně) až {@code max} (bez).
     *
     * @param max maximum
     * @return pseudonáhodné nezáporné celé číslo
     */
    public final int nextInt(final int max) {
        return Math.abs(nextInt() % max);
    }

    /**
     * Vrátí náhodnou logickou hodnotu.
     *
     * @return boolean
     */
    public final boolean nextBoolean() {
        return nextInt(2) == 0;
    }

    /**
     * Vrátí náhodný prvek pole.
     *
     * @param <E> typ prvků v poli
     * @param elements pole
     * @return náhodný prvek
     * @throws IllegalArgumentException v případě prázdného pole
     */
    @SuppressWarnings({"unchecked"})
    public <E> E randomElement(E... elements) {
        final int length = elements.length;

        if (length == 0) {
            throw new IllegalArgumentException("Empty array");

        } else if (length == 1) {
            return elements[0];

        } else {
            return elements[nextInt(length)];
        }
    }

    /**
     * Vrátí náhodný prvek pole.
     *
     * @param elements pole
     * @return náhodný prvek
     * @throws IllegalArgumentException v případě prázdného pole
     */
    public int randomElement(int... elements) {
        final int length = elements.length;

        if (length == 0) {
            throw new IllegalArgumentException("Empty array");

        } else if (length == 1) {
            return elements[0];

        } else {
            return elements[nextInt(length)];
        }
    }

    // --- STATICKÉ METODY ---

    /**
     * Vrátí pseudonáhodné celé číslo.
     *
     * @param seed hodnota, ze které je vypočteno výsledné pseudonáhodné číslo,
     *             pro {@code seed=0} bude výsledek vždy nulový
     * @return pseudonáhodné celé číslo
     */
    public static final int nextRandom(int seed) {
        seed ^= (seed << 6);
        seed ^= (seed >>> 21);
        seed ^= (seed << 7);

        return seed;
    }

    /**
     * Vrátí pseudonáhodné celé číslo v rozmezí 0 (včetně) až {@code max} (bez).
     *
     * @param seed hodnota, ze které je vypočteno výsledné pseudonáhodné číslo,
     *             pro {@code seed=0} bude výsledek vždy nulový
     * @param max maximum
     * @return pseudonáhodné nezáporné celé číslo
     */
    public static final int nextRandom(final int seed, final int max) {
        return Math.abs(nextRandom(seed) % max);
    }

    /**
     * Vrátí pseudonáhodné celé číslo.
     *
     * @param seed hodnota, ze které je vypočteno výsledné pseudonáhodné číslo,
     *             pro {@code seed=0} bude výsledek vždy nulový
     * @return pseudonáhodné celé číslo
     */
    public static final long nextRandom(long seed) {
        seed ^= (seed << 21);
        seed ^= (seed >>> 35);
        seed ^= (seed << 4);

        return seed;
    }

    /**
     * Vrátí pseudonáhodné celé číslo v rozmezí 0 (včetně) až {@code max} (bez).
     *
     * @param seed hodnota, ze které je vypočteno výsledné pseudonáhodné číslo,
     *             pro {@code seed=0} bude výsledek vždy nulový
     * @param max maximum
     * @return pseudonáhodné nezáporné celé číslo
     */
    public static final long nextRandom(final long seed, final long max) {
        return Math.abs(nextRandom(seed) % max);
    }

}