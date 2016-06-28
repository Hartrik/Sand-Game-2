package cz.hartrik.sg2.app.module.frame.module.tools;

import cz.hartrik.common.Exceptions;
import javafx.util.StringConverter;

/**
 * Implementace {@link StringConverter} pro {@link Integer}.
 *
 * @version 2016-06-28
 * @author Patrik Harag
 */
class StringConverterImpl extends StringConverter<Integer> {

    private final int min;
    private final int max;
    private final int def;

    protected StringConverterImpl(int min, int max, int def) {
        this.min = min;
        this.max = max;
        this.def = def;
    }

    @Override
    public String toString(Integer integer) {
        return String.valueOf(integer);
    }

    @Override
    public Integer fromString(String string) {
        String filtered = string.replaceAll("\\D+", "");
        int n = Exceptions.call(Integer::parseInt, filtered).orElse(def);

        if (n > max) {
            n = max;
        } else if (n < min) {
            n = min;
        }

        return n;
    }

}