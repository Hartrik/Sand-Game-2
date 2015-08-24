
package cz.hartrik.sg2.world.element.type;

import cz.hartrik.sg2.world.Element;

/**
 * Rozhraní pro elementy, které nejsou ve své základní podobě.
 * Např.: led -> tekutá voda, roztavené železo -> pevné železo aj.
 * Tím se liší od {@link Container}, který nějaký element pouze obaluje.
 * Když se k elementu zjišťuje zdrojový štětec, tak se porovnává pouze
 * základní element.
 * 
 * @version 2014-08-31
 * @author Patrik Harag
 * @param <E> typ základního elementu
 */
public interface Metamorphic<E extends Element> extends Element {
    
    /**
     * Vrátí základní stav elementu. Např.: u ledu by to byla voda.
     * 
     * @return základní element/stav
     */
    public E getBasicElement();
    
}