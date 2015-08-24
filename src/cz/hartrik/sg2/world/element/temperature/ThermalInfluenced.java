
package cz.hartrik.sg2.world.element.temperature;

import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;

/**
 * Rozhraní pro elementy, které mohou být ovlivněny ohněm.
 * 
 * @version 2014-05-15
 * @author Patrik Harag
 */
public interface ThermalInfluenced extends Element {
    
    /**
     * Na element působí teplota.
     * 
     * @param x horizontální pozice
     * @param y vertikální pozice
     * @param tools nástorje
     * @param world "svět"
     * @param degrees žár působící na element ve °C.
     * @param fire přítomnost ohně
     * @return došlo ke spálení nebo jiné změně elementu (např. roztavení)
     */
    public boolean temperature(int x, int y, Tools tools, World world,
            int degrees, boolean fire);
    
}