package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.solid.GluedBurnablePowder;
import cz.hartrik.sg2.world.element.temperature.BurnableDef;
import cz.hartrik.sg2.world.element.temperature.FireSettings;

/**
 * Abstraktní třída pro sypké hořlavé elementy.
 *
 * @version 2016-06-14
 * @author Patrik Harag
 */
public abstract class BurnablePowder extends PowderMid implements BurnableDef {

    private static final long serialVersionUID = 83715083867368_02_044L;

    private final FireSettings fireSettings;

    public BurnablePowder(Color color, int density, FireSettings fireSettings) {
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
    public abstract float getConductiveIndex();

    @Override
    public abstract float loss();

    // BurnableDef

    @Override
    public FireSettings getFireSettings() {
        return fireSettings;
    }

    // Glueable

    @Override
    public Element glue() {
        return new GluedBurnablePowder<>(this);
    }

}
