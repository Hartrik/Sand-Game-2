
package cz.hartrik.sg2.world.element;

import cz.hartrik.sg2.world.Element;

/**
 * Rozhraní pro element, který obaluje element jiný.
 *
 * @version 2014-04-01
 * @author Patrik Harag
 * @param <E> obalovaný element
 */
public interface Container<E extends Element> extends Element {

    /**
     * Vrátí obalený element.
     *
     * @return obalený element
     */
    public E getElement();

}