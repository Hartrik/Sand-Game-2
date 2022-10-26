
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.random.Chance;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.powder.PowderLow;

/**
 * Element představující mokrý cement. Pomalu tvrdne, až se z něho stane beton.
 * Tento mokrý cement se sype podobně jako písek.
 *
 * @version 2016-06-15
 * @author Patrik Harag
 */
public class CementWetPowder extends PowderLow {

    private static final long serialVersionUID = 83715083867368_02_006L;

    private final Element next;
    private final Chance chance;

    public CementWetPowder(Color color, int density, Chance chance, Element next) {
        super(color, density);
        this.chance = chance;
        this.next = next;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (temperature(x, y, tools, world, world.getTemperature(x, y)))
            return;

        if (super.testAction(x, y, tools, world)) {
            super.doAction(x, y, tools, world);
        } else {
            // pokud se nemůže hýbat, tak tuhne
            if (chance.nextBoolean())
                world.setAndChange(x, y, next);
        }
    }

    public boolean temperature(int x, int y, Tools tools, World world,
            float temperature) {

        if (temperature > 50) {
            // žár uspíší tuhnutí
            world.setAndChange(x, y, next);
            return true;
        }
        return false;
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return true;  // dokud se nezmění do dalšího stádia
    }

    @Override
    public boolean hasTemperature() {
        return true;
    }

    @Override
    public boolean isConductive() {
        return true;
    }

    @Override
    public float getConductiveIndex() {
        return 0.1f;
    }

    @Override
    public float loss() {
        return 0.0005f;
    }

}