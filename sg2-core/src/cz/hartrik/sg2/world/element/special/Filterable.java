package cz.hartrik.sg2.world.element.special;

import cz.hartrik.sg2.world.Element;

/**
 * Rozhraní pro elementy, které mohou projít filtrem.
 *
 * @version 2016-06-15
 * @author Patrik Harag
 */
public interface Filterable extends Element {

    /**
     * Test na filtraci - když vrátí <code>true</code> může projít filtrem.
     * Tato metoda slouží k tomu, aby bylo možné libovolně zpomalit průchod
     * příslušného elementu filtrem.
     *
     * @return boolean
     */
    public boolean testFilter();

    /**
     * Tato metoda je volána po vstupu elementu do filtru.
     *
     * @return nový (nebo stejný) element
     */
    public Filterable filter();

}