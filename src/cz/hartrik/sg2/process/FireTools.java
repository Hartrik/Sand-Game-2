
package cz.hartrik.sg2.process;

import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.temperature.Burnable;
import cz.hartrik.sg2.world.element.temperature.Flammable;
import cz.hartrik.sg2.world.factory.IFireFactory;

/**
 * Nástroje pro práci s ohněm a teplotou.
 *
 * @see Flammable
 * @see Burnable
 *
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class FireTools {

    private final World world;
    private final Tools tools;
    private final IFireFactory fireFactory;

    public FireTools(World world, Tools tools, IFireFactory fireFactory) {
        this.world = world;
        this.tools = tools;
        this.fireFactory = fireFactory;
    }

    public IFireFactory getFireFactory() {
        return fireFactory;
    }

    public final boolean findAir(int x, int y) {
        return world.valid(x+1, y) && world.get(x+1, y) instanceof Air
            || world.valid(x-1, y) && world.get(x-1, y) instanceof Air
            || world.valid(x, y+1) && world.get(x, y+1) instanceof Air
            || world.valid(x, y-1) && world.get(x, y-1) instanceof Air;
    }

    public final boolean affectBurnable(int x, int y, Burnable burnable, float temp) {
        if (temp > burnable.getDegreeOfFlammability()
                && burnable.getChanceToBurn()) {

            world.setAndChange(x, y, world.getBackground());
            world.setTemperature(x, y, World.DEFAULT_TEMPERATURE);
            return true;
        }
        return false;
    }

    /**
     * Zkusí zapálit hořlavý element.
     *
     * @param x horizontální pozice elementu
     * @param y vertikální pozice elementu
     * @param flammable hořlavý element
     * @param fire přítomnost ohně
     * @return došlo ke vznícení
     */
    public final boolean affectFlammable(int x, int y, Flammable flammable,
            boolean fire) {

        float temperature = world.getTemperature(x, y);
        return affectFlammable(x, y, flammable, temperature, fire);
    }

    /**
     * Vyvine teplotu na hořlavý element. Pokud je v okolí vzduch, tak se může
     * vznítit.
     *
     * @param x horizontální pozice elementu
     * @param y vertikální pozice elementu
     * @param flammable hořlavý element
     * @param temperature teplota působící na element
     * @param fire přítomnost ohně
     * @return došlo ke vznícení
     */
    public final boolean affectFlammable(int x, int y, Flammable flammable,
            float temperature, boolean fire) {

        if (temperature >= flammable.getDegreeOfFlammability()
                && flammable.getChanceToFlareUp()
                && (fire || findAir(x, y))) {

            world.setAndChange(x, y, fireFactory.getFireFocus(flammable));
            return true;
        }
        return false;
    }

}