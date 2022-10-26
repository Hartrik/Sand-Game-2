
package cz.hartrik.common.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotace umožňující definovat potřebné úkoly na balíček, třídu či její členy.
 * Může být na jeden element aplikována opakovaně. Anotace je odstraněna při
 * kompilaci.
 * <p>
 * 
 * <b>Příklady použití:</b>
 * <pre>
 * &#064;TODO
 * public class MyClass {
 *     ...
 * }
 * 
 * &#064;TODO("Dokončit")
 * &#064;TODO(value = "Vytvořit dokumentaci", type = TaskType.JAVADOC)
 * &#064;TODO(type = { TaskType.IMPLEMENT, TaskType.JAVADOC })
 * public class AnotherClass {
 *     ...
 * }</pre> 
 * 
 * @see TaskType
 * 
 * @version 2014-06-27
 * @author Patrik Harag
 */
@Repeatable(TODO.TODOs.class)
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD,
          ElementType.CONSTRUCTOR, ElementType.FIELD })
public @interface TODO {

    /**
     * Anotace umožňující implementaci {@link Repeatable}.
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target({ ElementType.ANNOTATION_TYPE })
    public @interface TODOs {
        TODO[] value();
    }

    String    value() default "";
    TaskType[] type() default TaskType.OTHER;

}