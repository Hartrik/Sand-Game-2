
package cz.hartrik.sg2.world.element.flora;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.random.Chance;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.powder.Soil;
import cz.hartrik.sg2.world.element.temperature.BurnableDef;
import cz.hartrik.sg2.world.element.temperature.FireAffected;
import cz.hartrik.sg2.world.element.temperature.FireSettings;

/**
 * Element představující trávu.
 *
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class Grass extends Plant implements BurnableDef, FireAffected {

    private static final long serialVersionUID = 83715083867368_02_015L;

    /** Maximální hustota elementu, který když spadne na trávu, tak ještě
       "přežije". */
    protected int maxElementDensity = 150;

    private final Color color;
    protected final int maxHeight;
    protected final Chance growChance;
    protected final Element[] deadGrass;
    protected final FireSettings fireSettings;

    public Grass(Color color, Chance chance, int maxHeight, Element[] deadGrass,
            FireSettings fireSettings) {

        this.color = color;
        this.growChance = chance;
        this.maxHeight = maxHeight;
        this.deadGrass = deadGrass;
        this.fireSettings = fireSettings;
    }

    @Override public Color getColor() { return color; }
    @Override public int getDensity() { return Integer.MAX_VALUE; }
    @Override public FireSettings getFireSettings() { return fireSettings; }

    protected final Element randomDeadGrass(Tools tools) {
        return deadGrass[tools.randomInt(deadGrass.length)];
    }

    // růst atd.

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (tools.getFireTools().affectFlammable(x, y, this, false))
            return;  // tráva začala hořet

        final int findSoil = findSoil(x, y, maxHeight, world);

        if (findSoil == SOIL_NOT_FOUND)
            // uschne - nemá půdu
            world.setAndChange(x, y, randomDeadGrass(tools));
        else
            checkElementAbove(x, y, findSoil, tools, world);
    }

    protected void checkElementAbove(int x, int y, int findSoil,
            Tools tools, World world) {

        final int up = y - 1;
        if (!world.valid(x, up))
            return;

        final Element element = world.get(x, up);
        if (element instanceof Air) {
            if (findSoil < maxHeight && growChance.nextBoolean())
                // vyroste
                world.setAndChange(x, up, this);

        } else if (!(element instanceof Grass
                || element.getDensity() < maxElementDensity)) {

            // uschne - nená vzduch
            world.setAndChange(x, y, randomDeadGrass(tools));
        }
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        if (tools.testTemperature(x, y))
            return true;

        // element pod
        if (!world.valid(x, y + 1)) return true;
        final Element under = world.get(x, y + 1);
        if (!(under instanceof Grass || under instanceof Soil))
            return true;

        // element nad
        if (!world.valid(x, y - 1)) return true;
        final Element over = world.get(x, y - 1);

        return (over instanceof Air)
                ? findSoil(x, y, maxHeight, world) < maxHeight
                : !(over instanceof Grass);
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

    // FireAffected

    @Override
    public void onFire(int x, int y, Tools tools, World world, float temp) {
        tools.getFireTools().affectFlammable(x, y, this, temp, true);
    }

}