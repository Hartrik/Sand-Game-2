package cz.hartrik.sg2.app.service;

import java.lang.annotation.*;

/**
 * Anotace, který slouží k určení <i>poskytovatele služeb</i>
 * ({@link ServiceProvider}), jehož služby jsou v označované třídě využívány.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@Repeatable(Require.RequireRepeatable.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
public @interface Require {

    /**
     * Anotace umožňující implementaci {@link Repeatable}.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE })
    @Documented
    public @interface RequireRepeatable {
        Require[] value();
    }

    Class<?> value();

}