
package cz.hartrik.sg2.world.element.flora;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.powder.Soil;
import cz.hartrik.sg2.world.element.temperature.FireSettings;

/**
 * Element představující trávu, která se sama rozšiřuje.
 *
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class WildGrass extends Grass {

    private static final long serialVersionUID = 83715083867368_02_036L;

    protected Grass[] grass;

    public WildGrass(Color color, Chance chance, int maxHeight,
            Element[] deadGrass, FireSettings fireSettings) {

        super(color, chance, maxHeight, deadGrass, fireSettings);
    }

    public void setGrass(Grass... grass) {
        this.grass = grass;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (tools.getFireTools().affectFlammable(x, y, this, false))
            return;  // tráva začala hořet

        final int findSoil = findSoil(x, y, maxHeight, world);

        if (findSoil == SOIL_NOT_FOUND) {
            // uschne - nemá půdu
            placeRandomDeadGrass(x, y, tools, world);

        } else if (findSoil == 1 && (growChance.nextBoolean())) {
            // rozšíří se do strany
            growNextTo(x, y, tools, world);

        } else {
            // roste
            super.checkElementAbove(x, y, findSoil, tools, world);
        }
    }

    private static enum Result { ERROR, WARNING, SUCCESS }

    private void growNextTo(int x, int y, Tools tools, World world) {
        if (grass == null || grass.length == 0) return;

        if (tools.randomBoolean()) {
            if (!trySide(x + 1, y, false, tools, world))
                 trySide(x - 1, y, false, tools, world);
        } else {
            if (!trySide(x - 1, y, false, tools, world))
                 trySide(x + 1, y, false, tools, world);
        }
    }

    private boolean trySide(int x, int y, boolean test,
            Tools tools, World world) {

        Result center = tryAt(x, y, test, tools, world);
        if (center == Result.SUCCESS) return true;
        if (center == Result.ERROR) return false;

        Result up = tryAt(x, y - 1, test, tools, world);
        if (up == Result.SUCCESS) return true;
        if (up == Result.ERROR) return false;

        return tryAt(x, y + 1, test, tools, world) == Result.SUCCESS;
    }

    private Result tryAt(int x, int y, boolean test,
            Tools tools, World world) {

        if (!world.valid(x, y)) return Result.WARNING;
        final Element element = world.get(x, y);
        if (element instanceof Grass) return Result.ERROR;
        else if (element instanceof Air) {
            final int yUnder = y + 1;
            if (!world.valid(x, yUnder)) return Result.WARNING;
            final Element under = world.get(x, yUnder);
            if (under instanceof Soil) {
                if (!test) world.setAndChange(x, y, randomGrass(tools));
                return Result.SUCCESS;
            } else
                return Result.WARNING;
        } else
            return Result.WARNING;
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        // element pod
        if (!world.valid(x, y + 1)) return true;
        Element under = world.get(x, y + 1);
        if (!(under instanceof Grass || under instanceof Soil))
            return true;

        // element nad
        if (!world.valid(x, y - 1)) return true;
        Element over = world.get(x, y - 1);
        if (over instanceof Air) {
            int findSoil = findSoil(x, y, maxHeight, world);
            if (findSoil < maxHeight) return true;
        } else if (over instanceof Grass) {
            if (!trySide(x + 1, y, false, tools, world))
                return trySide(x - 1, y, false, tools, world);
        } else {
            return true;
        }
        return false;
    }

    private Element randomGrass(Tools tools) {
        return grass[tools.randomInt(grass.length)];
    }

    private void placeRandomDeadGrass(int x, int y, Tools tools, World world) {
        Element randomDeadGrass = randomDeadGrass(tools);
        world.setAndChange(x, y, randomDeadGrass);
        if (!randomDeadGrass.hasTemperature())
            world.setTemperature(x, y, World.DEFAULT_TEMPERATURE);
    }

}