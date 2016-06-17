
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.sg2.world.Element;

/**
 * Rozhraní umožňující vytvořit z pohyblivého elementu statický. Jeho opak je
 * rozhraní {@link Destructible}.
 *
 * @version 2014-09-11
 * @author Patrik Harag
 */
public interface Glueable extends Element {

    /**
     * Vrátí statickou formu tohoto pohyblivého elementu.
     *
     * @return "slepený" element
     */
    public Element glue();

}