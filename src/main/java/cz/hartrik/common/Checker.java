
package cz.hartrik.common;

import cz.hartrik.common.reflect.LibraryClass;
import java.util.Collection;

/**
 * Obsahuje statické metody zejména pro kontrolu parametrů metod a
 * konstruktorů. <p>
 * 
 * Typy metod:
 * <ul>
 *   <li>
 *     <code>checkArgument</code> - testují správnost parametrů. Při nesplnění
 *     podmínky dojde k vyhození {@link IllegalArgumentException}.
 *   </li>
 *   <p>Implementované metody pro často používané případy:</p>
 *   <ul>
 *     <li>
 *       <code>requireNonNull</code> - objekt je testován na <code>null</code>
 *     </li>
 *     <li>
 *       <code>requireNonNullContent</code> - 1D/2D pole objektů a kolekce jsou
 *       testovány na přítomnost <code>null</code>
 *     </li>
 *     <li>
 *       <code>requireRange</code> - čísla jsou testována, zda se nachází v
 *       určitém rozmezí (včetně)
 *     </li>
 *     <li>
 *       <code>requireNonEmpty</code> - pole, kolekce a sekvence znaků jsou
 *       testovány, zda nejsou prázdné
 *     </li>
 *     <li>
 *       <code>requireLength</code> - pole a řetězce jsou testovány na délku
 *     </li>
 *   </ul>
 * 
 *   <li>
 *     <code>checkState</code> - testují stav objektu. Při nesplnění podmínky
 *     dojde k vyhození {@link IllegalStateException}.
 *   </li>
 * </ul>
 * 
 * Metody jsou pro primitivní datové typy a pole definovány zvlášť kvůli
 * rychlosti (autoboxing) a typové bezpečnosti (pole primitivních datových typů
 * by se jinak musela předávat jako {@link Object}).
 * 
 * @version 2014-08-23
 * @author Patrik Harag
 */
@LibraryClass
public final class Checker {

    private Checker() {}
    
    // --- Fields
    
    private static final String MESSAGE_NULL = "Object cannot be null";
    private static final String MESSAGE_NULL_ARRAY = "Array cannot contain null";
    private static final String MESSAGE_NULL_COLL = "Collection cannot contain null";
    
    private static final String MESSAGE_RANGE = "Argument out of bounds";
    
    private static final String MESSAGE_EMPTY_STRING = "String cannot be empty";
    private static final String MESSAGE_EMPTY_COLL = "Collection cannot be empty";
    private static final String MESSAGE_EMPTY_ARRAY = "Array cannot be empty";
    
    private static final String MESSAGE_LENGTH_STRING = "String wrong length";
    private static final String MESSAGE_LENGTH_ARRAY = "Array wrong length";
    
    private static final String MESSAGE_ERROR_STATE = "Incorrect object state";
    private static final String MESSAGE_ERROR_ARGUMENT = "Incorrect argument";
    
    // ------ ------ NULL CHECK ------ ------
    
    // Object
    
    /**
     * Testuje, zda-li objekt není <code>null</code>. Pokud ano, vyhodí
     * {@link NullPointerException}.
     * 
     * @throws NullPointerException pokud je objekt <code>null</code>
     * @param <T> typ objektu
     * @param object testovaný objekt
     * @return testovaný objekt
     */
    public static <T> T requireNonNull(T object) {
        return requireNonNull(object, MESSAGE_NULL);
    }
    
    /**
     * Testuje, zda-li objekt není <code>null</code>. Pokud ano, vyhodí
     * {@link NullPointerException}.
     * 
     * @throws NullPointerException pokud je objekt <code>null</code>
     * @param <T> typ objektu
     * @param object testovaný objekt
     * @param message detaily výjimky
     * @return testovaný objekt
     */
    public static <T> T requireNonNull(T object, Object message) {
        if (object == null)
            throw new NullPointerException(String.valueOf(message));
        
        return object;
    }
    
    // 1D array
    
    /**
     * Testuje, zda-li není některý z prvků pole <code>null</code>.
     * Pokud ano, vyhodí {@link NullPointerException}.
     * 
     * @throws NullPointerException pokud je některý prvek <code>null</code>
     * @param <T> typ pole
     * @param array testované pole
     * @return testované pole
     */
    public static <T> T[] requireNonNullContent(T[] array) {
        return requireNonNullContent(array, MESSAGE_NULL_ARRAY);
    }
    
    /**
     * Testuje, zda-li není některý z prvků pole <code>null</code>.
     * Pokud ano, vyhodí {@link NullPointerException}.
     * 
     * @throws NullPointerException pokud je některý prvek <code>null</code>
     * @param <T> typ pole
     * @param array testované pole
     * @param message detaily výjimky
     * @return testované pole
     */
    public static <T> T[] requireNonNullContent(T[] array, Object message) {
        for (T element : array)
            if (element == null)
                throw new NullPointerException(String.valueOf(message));
        
        return array;
    }
    
    // 2D array
    
    /**
     * Testuje, zda-li není některý z prvků pole <code>null</code>.
     * Pokud ano, vyhodí {@link NullPointerException}.
     * 
     * @throws NullPointerException pokud je některý prvek <code>null</code>
     * @param <T> typ pole
     * @param array testované pole
     * @return testované pole
     */
    public static <T> T[][] requireNonNullContent(T[][] array) {
        return requireNonNullContent(array, MESSAGE_NULL_ARRAY);
    }
    
    /**
     * Testuje, zda-li není některý z prvků pole <code>null</code>.
     * Pokud ano, vyhodí {@link NullPointerException}.
     * 
     * @throws NullPointerException pokud je některý prvek <code>null</code>
     * @param <T> typ pole
     * @param array testované pole
     * @param message detaily výjimky
     * @return testované pole
     */
    public static <T> T[][] requireNonNullContent(T[][] array, Object message) {
        for (T[] nestedArray : array)
            for (T element : nestedArray)
                if (element == null)
                    throw new NullPointerException(String.valueOf(message));
        
        return array;
    }
    
    // Collection
    
    /**
     * Testuje, zda-li není některý z prvků kolekce <code>null</code>.
     * Pokud ano, vyhodí {@link NullPointerException}.
     * 
     * @throws NullPointerException pokud je některý prvek <code>null</code>
     * @param <T> typ kolekce
     * @param collection testovaná kolekce
     * @return testovaná kolekce
     */
    public static <T extends Collection<?>> T requireNonNullContent(
            T collection) {
        
        return requireNonNullContent(collection, MESSAGE_NULL_COLL);
    }
    
    /**
     * Testuje, zda-li není některý z prvků kolekce <code>null</code>.
     * Pokud ano, vyhodí {@link NullPointerException}.
     * 
     * @throws NullPointerException pokud je některý prvek <code>null</code>
     * @param <T> typ kolekce
     * @param collection testovaná kolekce
     * @param message detaily výjimky
     * @return testovaná kolekce
     */
    public static <T extends Collection<?>> T requireNonNullContent(
            T collection, Object message) {
        
        for (Object element : collection)
            if (element == null)
                throw new NullPointerException(String.valueOf(message));
        
        return collection;
    }
    
    // ------ ------ RANGE CHECK ------ ------
    
    // generic
    
    /**
     * Testuje, zda-li je číselná hodnota ve vymezeném rozsahu.
     * Platí: <code>lower &le; n &le; upper</code>
     * 
     * @throws IllegalArgumentException pokud je číslo mimo rozsah
     * @param <T> typ čísla
     * @param n testovaná hodnota
     * @param lower spodní hranice
     * @param upper horní hranice
     * @return testovaná hodnota
     */
    public static <T extends Number & Comparable<T>> T requireRange(
            T n, T lower, T upper) {
        
        return requireRange(n, lower, upper, MESSAGE_RANGE);
    }
    
    /**
     * Testuje, zda-li je číselná hodnota ve vymezeném rozsahu.
     * Platí: <code>lower &le; n &le; upper</code>
     * 
     * @throws IllegalArgumentException pokud je číslo mimo rozsah
     * @param <T> typ čísla
     * @param n testovaná hodnota
     * @param lower spodní hranice
     * @param upper horní hranice
     * @param message detaily výjimky
     * @return testovaná hodnota
     */
    public static <T extends Number & Comparable<T>> T requireRange(
            T n, T lower, T upper, Object message) {
        
        if (n.compareTo(lower) == -1 || n.compareTo(upper) == 1)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return n;
    }
    
    // byte
    
    /** @see #requireRange(T, T, T) */
    public static byte requireRange(byte n, byte lower, byte upper) {
        return requireRange(n, lower, upper, MESSAGE_RANGE);
    }
    
    /** @see #requireRange(T, T, T, String) */
    public static byte requireRange(byte n, byte lower, byte upper,
            Object message) {
        
        if (n < lower || n > upper)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return n;
    }
    
    // short
    
    /** @see #requireRange(T, T, T) */
    public static short requireRange(short n, short lower, short upper) {
        return requireRange(n, lower, upper, MESSAGE_RANGE);
    }
    
    /** @see #requireRange(T, T, T, String) */
    public static short requireRange(short n, short lower, short upper,
            Object message) {
        
        if (n < lower || n > upper)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return n;
    }
    
    // int
    
    /** @see #requireRange(T, T, T) */
    public static int requireRange(int n, int lower, int upper) {
        return requireRange(n, lower, upper, MESSAGE_RANGE);
    }
    
    /** @see #requireRange(T, T, T, String) */
    public static int requireRange(int n, int lower, int upper,
            Object message) {
        
        if (n < lower || n > upper)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return n;
    }
    
    // long
    
    /** @see #requireRange(T, T, T) */
    public static long requireRange(long n, long lower, long upper) {
        return requireRange(n, lower, upper, MESSAGE_RANGE);
    }
    
    /** @see #requireRange(T, T, T, String) */
    public static long requireRange(long n, long lower, long upper,
            Object message) {
        
        if (n < lower || n > upper)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return n;
    }
    
    // float
    
    /** @see #requireRange(T, T, T) */
    public static float requireRange(float n, float lower, float upper) {
        return requireRange(n, lower, upper, MESSAGE_RANGE);
    }
    
    /** @see #requireRange(T, T, T, String) */
    public static float requireRange(float n, float lower, float upper,
            Object message) {
        
        if (n < lower || n > upper)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return n;
    }
    
    // double
    
    /** @see #requireRange(T, T, T) */
    public static double requireRange(double n, double lower, double upper) {
        return requireRange(n, lower, upper, MESSAGE_RANGE);
    }
    
    /** @see #requireRange(T, T, T, String) */
    public static double requireRange(double n, double lower, double upper,
            Object message) {
        
        if (n < lower || n > upper)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return n;
    }
    
    // ------ ------ EMPTY CHECK ------ ------
    
    // array - generic
    
    /**
     * Testuje, zda-li pole není prázdné.
     * 
     * @throws IllegalArgumentException pokud je pole prázdné
     * @param <T> typ pole
     * @param array testované pole
     * @return testované pole
     */
    public static <T> T[] requireNonEmpty(T[] array) {
        return requireNonEmpty(array, MESSAGE_EMPTY_ARRAY);
    }
    
    /**
     * Testuje, zda-li pole není prázdné.
     * 
     * @throws IllegalArgumentException pokud je pole prázdné
     * @param <T> typ pole
     * @param array testované pole
     * @param message detaily výjimky
     * @return testované pole
     */
    public static <T> T[] requireNonEmpty(T[] array, Object message) {
        if (array.length == 0)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - boolean
    
    /** @see #requireNonEmpty(T[]) */
    public static boolean[] requireNonEmpty(boolean[] array) {
        return requireNonEmpty(array, MESSAGE_EMPTY_ARRAY);
    }
    
    /** @see #requireNonEmpty(T[], String) */
    public static boolean[] requireNonEmpty(boolean[] array, Object message) {
        if (array.length == 0)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - char
    
    /** @see #requireNonEmpty(T[]) */
    public static char[] requireNonEmpty(char[] array) {
        return requireNonEmpty(array, MESSAGE_EMPTY_ARRAY);
    }
    
    /** @see #requireNonEmpty(T[], String) */
    public static char[] requireNonEmpty(char[] array, Object message) {
        if (array.length == 0)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - byte
    
    /** @see #requireNonEmpty(T[]) */
    public static byte[] requireNonEmpty(byte[] array) {
        return requireNonEmpty(array, MESSAGE_EMPTY_ARRAY);
    }
    
    /** @see #requireNonEmpty(T[], String) */
    public static byte[] requireNonEmpty(byte[] array, Object message) {
        if (array.length == 0)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - short
    
    /** @see #requireNonEmpty(T[]) */
    public static short[] requireNonEmpty(short[] array) {
        return requireNonEmpty(array, MESSAGE_EMPTY_ARRAY);
    }
    
    /** @see #requireNonEmpty(T[], String) */
    public static short[] requireNonEmpty(short[] array, Object message) {
        if (array.length == 0)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - int
    
    /** @see #requireNonEmpty(T[]) */
    public static int[] requireNonEmpty(int[] array) {
        return requireNonEmpty(array, MESSAGE_EMPTY_ARRAY);
    }
    
    /** @see #requireNonEmpty(T[], String) */
    public static int[] requireNonEmpty(int[] array, Object message) {
        if (array.length == 0)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - long
    
    /** @see #requireNonEmpty(T[]) */
    public static long[] requireNonEmpty(long[] array) {
        return requireNonEmpty(array, MESSAGE_EMPTY_ARRAY);
    }
    
    /** @see #requireNonEmpty(T[], String) */
    public static long[] requireNonEmpty(long[] array, Object message) {
        if (array.length == 0)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - float
    
    /** @see #requireNonEmpty(T[]) */
    public static float[] requireNonEmpty(float[] array) {
        return requireNonEmpty(array, MESSAGE_EMPTY_ARRAY);
    }
    
    /** @see #requireNonEmpty(T[], String) */
    public static float[] requireNonEmpty(float[] array, Object message) {
        if (array.length == 0)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - double
    
    /** @see #requireNonEmpty(T[]) */
    public static double[] requireNonEmpty(double[] array) {
        return requireNonEmpty(array, MESSAGE_EMPTY_ARRAY);
    }
    
    /** @see #requireNonEmpty(T[], String) */
    public static double[] requireNonEmpty(double[] array, Object message) {
        if (array.length == 0)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // Collection
    
    /**
     * Testuje, zda-li kolekce není prázdná.
     * 
     * @throws IllegalArgumentException pokud je kolekce prázdné
     * @param <T> typ kolekce
     * @param collection testovaná kolekce
     * @return testovaná kolekce
     */
    public static <T extends Collection<?>> T requireNonEmpty(T collection) {
        return requireNonEmpty(collection, MESSAGE_EMPTY_COLL);
    }
    
    /**
     * Testuje, zda-li kolekce není prázdná.
     * 
     * @throws IllegalArgumentException pokud je kolekce prázdné
     * @param <T> typ kolekce
     * @param collection testovaná kolekce
     * @param message detaily výjimky
     * @return testovaná kolekce
     */
    public static <T extends Collection<?>> T requireNonEmpty(T collection,
            Object message) {
        
        if (collection.isEmpty())
            throw new IllegalArgumentException(String.valueOf(message));
        
        return collection;
    }
    
    // String
    
    /**
     * Testuje, zda-li sekvence znaků není prázdná.
     * 
     * @throws IllegalArgumentException pokud je sekvence prázdné
     * @param <T> typ sekvence
     * @param sequence testovaná sekvence
     * @return testovaná sekvence
     */
    public static <T extends CharSequence> T requireNonEmpty(T sequence) {
        return requireNonEmpty(sequence, MESSAGE_EMPTY_STRING);
    }
    
    /**
     * Testuje, zda-li sekvence znaků není prázdná.
     * 
     * @throws IllegalArgumentException pokud je sekvence prázdné
     * @param <T> typ sekvence
     * @param sequence testovaná sekvence znaků
     * @param message detaily výjimky
     * @return testovaná sekvence
     */
    public static <T extends CharSequence> T requireNonEmpty(T sequence,
            Object message) {
        
        if (sequence.length() == 0)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return sequence;
    }
    
    // ------ ------ LENGTH CHECK ------ ------
    
    // array - generic
    
    /**
     * Testuje délku pole.
     * 
     * @throws IllegalArgumentException nesprávná délka
     * @param <T> typ testovaného generického pole
     * @param array testované pole
     * @param length požadovaná délka pole
     * @return testované pole
     */
    public static <T> T[] requireLength(T[] array, int length) {
        return requireLength(array, length, MESSAGE_LENGTH_ARRAY);
    }
    
    /**
     * Testuje délku pole.
     * 
     * @throws IllegalArgumentException nesprávná délka
     * @param <T> typ testovaného generického pole
     * @param array testované pole
     * @param length požadovaná délka pole
     * @param message detaily výjimky
     * @return testované pole
     */
    public static <T> T[] requireLength(T[] array, int length, Object message) {
        if (array.length != length)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }

    // array - boolean
    
    /** @see #requireLength(T[], int) */
    public static boolean[] requireLength(boolean[] array, int length) {
        return requireLength(array, length, MESSAGE_LENGTH_ARRAY);
    }
    
    /** @see #requireLength(T[], int, Object) */
    public static boolean[] requireLength(boolean[] array, int length,
            Object message) {
        
        if (array.length != length)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - char
    
    /** @see #requireLength(T[], int) */
    public static char[] requireLength(char[] array, int length) {
        return requireLength(array, length, MESSAGE_LENGTH_ARRAY);
    }
    
    /** @see #requireLength(T[], int, Object) */
    public static char[] requireLength(char[] array, int length,
            Object message) {
        
        if (array.length != length)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - byte
    
    /** @see #requireLength(T[], int) */
    public static byte[] requireLength(byte[] array, int length) {
        return requireLength(array, length, MESSAGE_LENGTH_ARRAY);
    }
    
    /** @see #requireLength(T[], int, Object) */
    public static byte[] requireLength(byte[] array, int length,
            Object message) {
        
        if (array.length != length)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - short
    
    /** @see #requireLength(T[], int) */
    public static short[] requireLength(short[] array, int length) {
        return requireLength(array, length, MESSAGE_LENGTH_ARRAY);
    }
    
    /** @see #requireLength(T[], int, Object) */
    public static short[] requireLength(short[] array, int length,
            Object message) {
        
        if (array.length != length)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - int
    
    /** @see #requireLength(T[], int) */
    public static int[] requireLength(int[] array, int length) {
        return requireLength(array, length, MESSAGE_LENGTH_ARRAY);
    }
    
    /** @see #requireLength(T[], int, Object) */
    public static int[] requireLength(int[] array, int length, Object message) {
        
        if (array.length != length)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - long
    
    /** @see #requireLength(T[], int) */
    public static long[] requireLength(long[] array, int length) {
        return requireLength(array, length, MESSAGE_LENGTH_ARRAY);
    }
    
    /** @see #requireLength(T[], int, Object) */
    public static long[] requireLength(long[] array, int length,
            Object message) {
        
        if (array.length != length)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - float
    
    /** @see #requireLength(T[], int) */
    public static float[] requireLength(float[] array, int length) {
        return requireLength(array, length, MESSAGE_LENGTH_ARRAY);
    }
    
    /** @see #requireLength(T[], int, Object) */
    public static float[] requireLength(float[] array, int length,
            Object message) {
        
        if (array.length != length)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // array - double
    
    /** @see #requireLength(T[], int) */
    public static double[] requireLength(double[] array, int length) {
        return requireLength(array, length, MESSAGE_LENGTH_ARRAY);
    }
    
    /** @see #requireLength(T[], int, Object) */
    public static double[] requireLength(double[] array, int length,
            Object message) {
        
        if (array.length != length)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return array;
    }
    
    // String
    
    /**
     * Testuje délku řetězce.
     * 
     * @throws IllegalArgumentException nesprávná délka
     * @param string řetězec k testování
     * @param length vyžadovaný délka pole
     * @return testovaný řetězec
     */
    public static String requireLength(String string, int length) {
        return requireLength(string, length, MESSAGE_LENGTH_STRING);
    }
    
    /**
     * Testuje délku řetězce.
     * 
     * @throws IllegalArgumentException nesprávná délka
     * @param string řetězec k testování
     * @param length vyžadovaný délka pole
     * @param message detaily výjimky
     * @return testovaný řetězec
     */
    public static String requireLength(String string, int length,
            Object message) {
        
        if (string.length() != length)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return string;
    }
    
    // ------ ------ STATE CHECK ------ ------
    
    /**
     * Testuje stav objektu vlastním testem.
     * Pokud je výsledek testu <code>true</code>, je vrácen testovaný objekt,
     * pokud <code>false</code>, je vyhozena výjimka.
     * 
     * @throws IllegalStateException test neprojde
     * @param <T> typ testovaného objektu
     * @param object testovaný objekt
     * @param expression výraz vyhodnocený jako <code>boolean</code>
     * @return testovaný objekt
     */
    public static <T> T checkState(T object, boolean expression) {
        return checkState(object, expression, MESSAGE_ERROR_STATE);
    }
    
    /**
     * Testuje stav objektu vlastním testem.
     * Pokud je výsledek testu <code>true</code>, je vrácen testovaný objekt,
     * pokud <code>false</code>, je vyhozena výjimka.
     * 
     * @throws IllegalStateException test neprojde
     * @param <T> typ testovaného objektu
     * @param object testovaný objekt
     * @param expression výraz vyhodnocený jako <code>boolean</code>
     * @param message detaily výjimky
     * @return testovaný objekt
     */
    public static <T> T checkState(T object, boolean expression,
            Object message) {
        
        if (!expression)
            throw new IllegalStateException(String.valueOf(message));
        
        return object;
    }
    
    // ------ ------ ARGUMENT CHECK ------ ------
    
    /**
     * Testuje parametr vlastním testem.
     * Pokud je výsledek testu <code>true</code>, je vrácen testovaný objekt,
     * pokud <code>false</code>, je vyhozena výjimka.
     * 
     * @throws IllegalArgumentException test neprojde
     * @param <T> typ testovaného objektu
     * @param object testovaný objekt
     * @param expression výraz vyhodnocený jako <code>boolean</code>
     * @return testovaný objekt
     */
    public static <T> T checkArgument(T object, boolean expression) {
        return checkState(object, expression, MESSAGE_ERROR_ARGUMENT);
    }
    
    /**
     * Testuje parametr vlastním testem.
     * Pokud je výsledek testu <code>true</code>, je vrácen testovaný objekt,
     * pokud <code>false</code>, je vyhozena výjimka.
     * 
     * @throws IllegalArgumentException test neprojde
     * @param <T> typ testovaného objektu
     * @param object testovaný objekt
     * @param expression výraz vyhodnocený jako <code>boolean</code>
     * @param message detaily výjimky
     * @return testovaný objekt
     */
    public static <T> T checkArgument(T object, boolean expression,
            Object message) {
        
        if (!expression)
            throw new IllegalArgumentException(String.valueOf(message));
        
        return object;
    }
    
    // TODO primitives
    
}