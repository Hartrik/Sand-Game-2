
package cz.hartrik.common.reflect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotace slouží k označení třídy, která obsahuje pouze statické metody, má
 * modifikátor <code>final</code> a neumožňuje tvorbu instancí.
 * Anotace se z kódu odstraní při kompilaci, v dokumentaci zůstává.
 * 
 * @version 2014-08-23
 * @author Patrik Harag
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Documented
public @interface LibraryClass {

}