package cz.hartrik.sg2.brush;

/**
 * Štětce se do sebe často skládají, ale někdy je důležité dostat se k původnímu
 * štětci, proto existuje toto rozhraní.
 *
 * @version 2015-11-19
 * @author Patrik Harag
 */
public interface Wrapper {

    /**
     * Vrátí původní obalený štětec.
     *
     * @return štětec
     */
    Brush getOriginal();

    public static boolean isInstance(Brush brush, Class<?> clazz) {
        if (clazz.isInstance(brush))
            return true;

        if (brush instanceof Wrapper)
            return isInstance(((Wrapper) brush).getOriginal(), clazz);

        return false;
    }

    public static Brush unwrap(Brush brush) {
        if (brush instanceof Wrapper)
            return unwrap(((Wrapper) brush).getOriginal());

        return brush;
    }

}