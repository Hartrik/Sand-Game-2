package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.temperature.BurnableDef;
import cz.hartrik.sg2.world.element.temperature.FireSettings;

/**
 * Element představující zapálený knot, není možné ho uhasit.
 *
 * @version 2016-06-15
 * @author Patrik Harag
 */
public class WickBurning extends Wick implements BurnableDef {

    private static final long serialVersionUID = 83715083867368_02_053L;

    public WickBurning(Color color, FireSettings fireSettings) {
        super(color, fireSettings);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        onFire(x, y, tools, world, getFlammableNumber());
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return true;
    }

    @Override
    public void onFire(int x, int y, Tools tools, World world, float temp) {
        if (!tools.getFireTools().affectFlammable(
                x, y, this, getFlammableNumber(), false)) {

            if (getChanceToBurn()) {
                world.setAndChange(x, y, world.getBackground());
                world.setTemperature(x, y, World.DEFAULT_TEMPERATURE);
            }
        }

        affectNears(x, y, tools, world);
    }

}