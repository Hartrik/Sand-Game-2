package cz.hartrik.sg2.world.element.type;

import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;

/**
 * Rozhraní pro elementy, které mohou projít filtrem.
 * 
 * @version 2015-02-15
 * @author Patrik Harag
 */
public interface Filterable extends Element {

    /**
     * Testuje filtraci.
     * 
     * @return vrací <code>true</code> pokud může být nyní filtrován
     */
    public boolean filter();

    /**
     * Tato metoda je volána po vstupu elementu do filtru.
     * 
     * @param x horizontální pozice v poli
     * @param y vertikální pozice v poli
     * @param tools pomocné nástroje
     * @param world "svět", na kterém se element nachází
     * @return nový element, který zůstane na místě po jeho vstupu do filtru
     */
    public Element onFilter(int x, int y, Tools tools, World world);

}