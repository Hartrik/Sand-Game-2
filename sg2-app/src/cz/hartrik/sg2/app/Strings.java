
package cz.hartrik.sg2.app;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class manages externalized strings.
 *
 * @author Patrik Harag
 * @version 2017-07-05
 */
public class Strings {

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class SingletonHolder {
        private static final Strings INSTANCE = new Strings();
    }

    private static Strings getInstance() {
        return SingletonHolder.INSTANCE;
    }


    // .properties files are stored in the same package
    private static final String PACKAGE = Strings.class.getPackage().getName();
    private static final String BASE_NAME = PACKAGE + ".strings";

    private final ResourceBundle resourceBundle;

    private Strings() {
        Locale locale = Locale.getDefault();

        this.resourceBundle = ResourceBundle.getBundle(BASE_NAME, locale);
    }

    /**
     * Gets a string for the given key from resource bundle.
     *
     * @param key
     * @return value
     */
    public static String get(String key) {
        try {
            return getInstance().resourceBundle.getString(key);

        } catch (MissingResourceException exc) {
            System.err.println("Property not found: '" + key + "'");
            return key;  // better than exceptions everywhere
        }
    }

    /**
     * Gets a string for the given key from resource bundle.
     * Calling this method is equivalent to calling
     *    <pre>{@code MessageFormat.format(Strings.get(key), args)}</pre>
     *
     * @param key
     * @param args formatter arguments
     * @return formatted value
     */
    public static String get(String key, Object... args) {
        return MessageFormat.format(get(key), args);
    }

    public static ResourceBundle getResourceBundle() {
        return getInstance().resourceBundle;
    }
}
