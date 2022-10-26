
package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.SolidElement;

/**
 * Element představující topné těleso. <p>
 *
 * Funguje tak, že na své pozici stále "doplňuje" teplotu a okolní elementy
 * se tak ohřívají.
 *
 * @version 2016-06-14
 * @author Patrik Harag
 */
public class Heater extends SolidElement {

    private static final long serialVersionUID = 83715083867368_02_016L;

    private final Color color;
    private final float temperature;

    public Heater(Color color, float temperature) {
        this.color = color;
        this.temperature = temperature;
    }

    public float getStaticTemperature() {
        return temperature;
    }

    // Element

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        world.setTemperature(x, y, temperature);
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return world.getTemperature(x, y) != temperature;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean hasTemperature() {
        return true;
    }

    @Override
    public boolean isConductive() {
        return false;
    }

    @Override
    public float loss() {
        return 0;
    }

}