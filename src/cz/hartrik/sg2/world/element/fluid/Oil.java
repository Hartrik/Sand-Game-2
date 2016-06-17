
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.temperature.BurnableDef;
import cz.hartrik.sg2.world.element.temperature.FireSettings;

/**
 * Element představující hořlavou kapalinu.
 *
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class Oil extends FluidWater implements BurnableDef {

    private static final long serialVersionUID = 83715083867368_02_042L;

    private final FireSettings fireSettings;

    public Oil(Color color, int density, FireSettings fireSettings) {
        super(color, density);
        this.fireSettings = fireSettings;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (!tools.getFireTools().affectFlammable(x, y, this, false))
            super.doAction(x, y, tools, world);
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.testTemperature(x, y)
                || super.testAction(x, y, tools, world);
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
        return 0.4f;
    }

    @Override
    public float loss() {
        return 0.00001f;
    }

    // BurnableDef

    @Override
    public FireSettings getFireSettings() {
        return fireSettings;
    }

}