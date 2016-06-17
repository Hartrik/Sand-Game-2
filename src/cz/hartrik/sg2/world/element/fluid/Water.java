
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.common.random.RatioChance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.BasicElement;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;

/**
 * Element představující vodu.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public class Water extends BoilingFluid implements Dryable {

    private static final long serialVersionUID = 83715083867368_02_034L;

    public static final int BOILING_POINT = 100;

    protected final Chance chance;
    protected Element[] vaporized = { BasicElement.AIR };

    public Water(Color color, int density, Chance chance) {
        super(color, density, BOILING_POINT);
        this.chance = chance;
    }

    public void setVaporized(Element... vaporized) {
        this.vaporized = vaporized;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        float temperature = world.getTemperature(x, y);
        if (!vapor(x, y, tools, world, temperature))
            // pokud se nezmění na páru, bude se chovat jako voda...
            super.doAction(x, y, tools, world);
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.testTemperature(x, y)
                || super.testAction(x, y, tools, world);
    }

    protected boolean vapor(int x, int y, Tools tools, World world, float degrees) {
        if (testVapor(degrees)) {
            world.setAndChange(x, y, vaporized[tools.randomInt(vaporized.length)]);
            return true;
        }
        return false;
    }

    private boolean testVapor(float temp) {
        if (temp < 100)
            return false;

        return((temp > 1500)
                ? true
                : RatioChance.nextBoolean((int) ((1500 - temp) * 3)));
    }

    @Override
    public float getConductiveIndex() {
        return 0.5f;
    }

    @Override
    public float loss() {
        return 0.000001f;
    }

    @Override
    public Color getColor(float temperature) {
        double a = 0;

        if (temperature > 90) {
            a = temperature / 3000.;
            if (a > 0.5) a = 0.5;
        }

        final Color base = getColor();
        return new Color(base.getDoubleRed(), a, base.getDoubleBlue());
    }

    // Dryable

    @Override
    public Element dry() {
        return null;
    }

}