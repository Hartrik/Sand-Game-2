
package cz.hartrik.common.reflect;

/**
 * Výčtový typ představující typ úkolu anotace {@link TODO}.
 *
 * @version 2014-06-27
 * @author Patrik Harag
 */
public enum TaskType {

    /** Je potřeba implementovat obsah */
    IMPLEMENT,

    /** Je potřeba refaktorovat */
    REFACTOR,

    /** Je potřeba vytvořit nebo upravit dokumentaci */
    JAVADOC,

    /** Nedefinovaný úkol */
    OTHER;

}