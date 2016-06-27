
package cz.hartrik.sg2.world.element.gas;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.random.Chance;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.temperature.BurnableDef;
import cz.hartrik.sg2.world.element.temperature.FireSettings;

/**
 * Element představující zemní plyn.
 *
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class NaturalGas extends SimpleGasElement implements BurnableDef {

    private static final long serialVersionUID = 83715083867368_02_043L;

    protected final FireSettings fireSettings;

    public NaturalGas(Color color, int density, Chance chanceToMoveUp,
            FireSettings fireSettings) {

        super(color, density, chanceToMoveUp);
        this.fireSettings = fireSettings;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (!tools.getFireTools().affectFlammable(x, y, this, false))
            super.doAction(x, y, tools, world);
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
        return 0.5f;
    }

    @Override
    public float loss() {
        return 0.001f;
    }

    // BurnableDef

    @Override
    public FireSettings getFireSettings() {
        return fireSettings;
    }

}