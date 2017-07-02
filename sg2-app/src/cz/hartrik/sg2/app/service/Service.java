package cz.hartrik.sg2.app.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotace sloužící k označení metody, která je službou.
 * Metoda označení touto anotací musí mít jeden parametr typu
 * {@link cz.hartrik.sg2.app.module.frame.Application}.
 * Třída definující služby musí být označena anotací {@link ServiceProvider}.
 *
 * @see ServiceProvider
 * @see Require
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
public @interface Service {

    String value();

}