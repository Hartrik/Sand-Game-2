
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.common.random.RatioChance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.fluid.SaltWater;
import cz.hartrik.sg2.world.element.fluid.Water;
import cz.hartrik.sg2.world.factory.ISingleInputFactory;

/**
 * Element představující sůl.
 *
 * @version 2016-06-15
 * @author Patrik Harag
 */
public class Salt extends PowderMid {

    private static final long serialVersionUID = 83715083867368_02_022L;

    protected static final Chance SALT_RATIO = new RatioChance(7);

    private final ISingleInputFactory<Salt, SaltWater> saltWaterFactory;

    public Salt(Color color, int density,
            ISingleInputFactory<Salt, SaltWater> saltWaterFactory) {

        super(color, density);
        this.saltWaterFactory = saltWaterFactory;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (!wet(x, y, tools, world))
            super.doAction(x, y, tools, world);
    }

    protected boolean wet(int x, int y, Tools tools, World world) {
        return !tools.getDirVisitor().visitWhile(x, y,
                (Element element, int nX, int nY) -> {

            if (element instanceof Water && !(element instanceof SaltWater)) {
                if (SALT_RATIO.nextBoolean()) {
                    // sůl mizí
                    world.setAndChange(x, y, saltWaterFactory.apply(this));
                    world.setAndChange(nX, nY, world.getBackground());
                    world.setTemperature(nX, nY, World.DEFAULT_TEMPERATURE);

                } else {
                    // sůl zůstává
                    world.setAndChange(nX, nY, saltWaterFactory.apply(this));
                }
                return false;
            }
            return true;
        }, tools.randomDirection());
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.testTemperature(x, y)
                || super.testAction(x, y, tools, world);

        // testovat přítomnost vody je zbytečné
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
        return 0.3f;
    }

    @Override
    public float loss() {
        return 0.0001f;
    }

}