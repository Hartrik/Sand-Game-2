package cz.hartrik.sg2.app.module.frame.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotace slouží ke značení tříd, které poskytují služby.
 *
 * @see Service
 * @see Require
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
public @interface ServiceProvider {

}