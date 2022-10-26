
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.temperature.FireAffected;
import cz.hartrik.sg2.world.element.temperature.FireSettings;

/**
 * Element představující knot. Pokud je zapálen, už nelze ho nelze uhasit.
 *
 * @version 2016-06-15
 * @author Patrik Harag
 */
public class Wick extends SolidElement implements FireAffected {

    private static final long serialVersionUID = 83715083867368_02_071L;

    private final Color color;
    private final FireSettings fs;

    public Wick(Color color, FireSettings fireSettings) {
        this.color = color;
        this.fs = fireSettings;
    }

    @Override
    public final Color getColor() {
        return color;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
    }

    @Override
    public boolean hasTemperature() {
        return true;
    }

    // FireAffected

    @Override
    public void onFire(int x, int y, Tools tools, World world, float temp) {
        world.setTemperature(x, y, fs.getFlammableNumber());

        affectNears(x, y, tools, world);

        world.setTemperature(x, y, 0);
        world.setAndChange(x, y, world.getBackground());
    }

    protected void affectNears(int x, int y, Tools tools, World world) {
        tools.getDirVisitor().visit(x, y, (element, ex, ey) -> {
            if (element instanceof Wick && !(element instanceof WickBurning)) {
                Wick next = (Wick) element;
                Wick burning = new WickBurning(next.color, fs);

                world.setTemperature(ex, ey, fs.getFlammableNumber());
                world.setAndChange(ex, ey, burning);
            }
        }, Direction.values());
    }

    public FireSettings getFireSettings() {
        return fs;
    }

}