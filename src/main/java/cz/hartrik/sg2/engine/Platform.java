package cz.hartrik.sg2.engine;

/**
 *
 * @author Patrik Harag
 * @version 2017-08-06
 */
public class Platform {

    private static PlatformProvider current = null;

    public static void init(PlatformProvider platformProvider) {
        current = platformProvider;
    }

    public static PlatformProvider get() {
        if (current == null) {
            throw new IllegalStateException("Platform not initialized!");
        }

        return current;
    }

}
