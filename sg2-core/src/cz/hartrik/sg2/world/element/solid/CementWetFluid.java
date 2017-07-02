
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.random.Chance;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.fluid.Dryable;
import cz.hartrik.sg2.world.element.fluid.FluidDense;

/**
 * Element představující mokrý cement. Pomalu tvrdne, až se z něho stane beton.
 * Tento mokrý cement se chová jako kapalina.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public class CementWetFluid extends FluidDense implements Dryable {

    private static final long serialVersionUID = 83715083867368_02_005L;

    private final Element next;
    private final Chance chance;

    public CementWetFluid(Color color, int density, Chance chance, Element next) {
        super(color, density);
        this.chance = chance;
        this.next = next;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (temperature(x, y, tools, world, world.getTemperature(x, y)))
            return;

        if (super.testAction(x, y, tools, world)) {
            int ny = y + Direction.DOWN.getY();

            if (ny == world.getHeight() || world.get(x, ny) instanceof SolidElement) {
                // pokud se element dostane na dolní okraj plátna nebo
                // je pod ním statický element, tak ztuhne
                world.setAndChange(x, y, next);
                return;
            }

            // pohybuje se jako kapalina
            super.doAction(x, y, tools, world);

        } else {
            // pokud se nemůže hýbat, tak to urychlí tuhnutí
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

    // Dryable

    @Override
    public Element dry() {
        return next;
    }

}