package cz.hartrik.sg2.world.element.temperature;

import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;

/**
 * Rozhraní pro elementy, které jsou ovlivňovány ohněm.
 * <i>(Nikoli teplotou, ale přímo ohněm.)</i>
 *
 * @version 2016-06-15
 * @author Patrik Harag
 */
public interface FireAffected extends Element {

    /**
     * Tato metoda je volána když se elementu "dotkne" oheň.
     *
     * @param x horizontální pozice elementu
     * @param y vertikální pozice elementu
     * @param tools nástroje
     * @param world plátno
     * @param temp teplota ohně
     */
    public void onFire(int x, int y, Tools tools, World world, float temp);

}