
package cz.hartrik.sg2.world.element.temperature;

import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Effect;

/**
 * Abstraktní třída pro ohnivé elementy/efekty.
 *
 * @version 2014-04-01
 * @author Patrik Harag
 */
public abstract class FireElement extends Effect {

    private static final long serialVersionUID = 83715083867368_02_039L;

    public abstract int getTemperature();

    /**
     * Vrátí teplotu bodu.
     *
     * @param x horizontální pozice
     * @param y vertikální pozice
     * @param world "svět"
     * @return teplota bodu, pokud bod neexistuje, nebo nemá teplotu, vrací 0
     */
    protected int getTemperatureAt(int x, int y, World world) {
        if (world.valid(x, y)) {
            Element element = world.get(x, y);
            if (element instanceof Fire)
                return ((FireElement) element).getTemperature();
        }
        return 0;
    }

}