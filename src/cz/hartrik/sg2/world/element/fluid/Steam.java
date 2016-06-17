
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.gas.SimpleGasElement;
import cz.hartrik.sg2.world.element.temperature.Fire;
import cz.hartrik.sg2.world.element.temperature.FireAffected;

/**
 * Element představující vodní páru.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public class Steam extends SimpleGasElement implements FireAffected, Dryable {

    private static final long serialVersionUID = 83715083867368_02_047L;

    private final Water water;

    public Steam(Color color, int density, Chance chanceToMoveUp,
            Water water) {

        super(color, density, chanceToMoveUp);
        this.water = water;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        float temperature = world.getTemperature(x, y);

        if (turnIntoWater(temperature, tools))
            world.setAndChange(x, y, water);
        else
            super.doAction(x, y, tools, world);
    }

    private boolean turnIntoWater(float temperature, Tools tools) {
        if (temperature < 100)
            return true;

        float t = temperature * temperature - 9_500;
        return tools.randomInt((int) t) == 0;
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return true;
    }

    @Override
    public boolean hasTemperature() {
        return water.hasTemperature();
    }

    @Override
    public boolean isConductive() {
        return water.isConductive();
    }

    @Override
    public float getConductiveIndex() {
        return water.getConductiveIndex();
    }

    @Override
    public float loss() {
        return water.loss();
    }

    // FireAffected

    @Override
    public void onFire(int x, int y, Tools tools, World world, float temp) {
        // zajistí rychlý průchod přes plameny
        // jinak by částečky páry mohly zůstat viset uprostřed ohně

        for (int ny = y - 1; ny >= 0; ny--) {
            Element next = world.get(x, ny);

            if (next instanceof Fire)
                continue;

            if (next instanceof Air)
                tools.swap(x, y, this, x, ny, next);

            return;
        }
    }

    // Dryable

    @Override
    public Element dry() {
        return null;
    }

}