
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.sg2.world.Element;

/**
 * Rozhraní pro pevné elementy, které mohou být zničeny na prach nebo něco
 * podobného. Jeho opakem je rozhraní {@link Glueable}.
 * 
 * @version 2014-09-11
 * @author Patrik Harag
 */
public interface Destructible extends Element {
    
//    /**
//     * Pokusí se element rozbít. Pokud bude element úspěšně rozbit, vrátí
//     * <code>true</code> a umístí nové element do mapy.
//     * 
//     * @param x horizontální pozice elementu
//     * @param y vertikální pozice elementu
//     * @param tools nástroje
//     * @param world mapa
//     * @param force síla vyvinutá na element
//     * @return úspěšnost
//     */
//    public boolean destroy(int x, int y, Tools tools, World world, int force);
    
    /**
     * Vrátí pohyblivou - sypkou/kapalnou formu tohoto elementu.
     * 
     * @return nový (rozbitý) element
     */
    public Element destroy();
    
}