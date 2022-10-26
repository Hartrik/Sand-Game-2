package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.temperature.BurnableDef;
import cz.hartrik.sg2.world.element.temperature.FireSettings;
import cz.hartrik.sg2.world.element.Organic;

/**
 * Element představující dřevo.
 *
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class Wood extends SolidElement implements Organic, BurnableDef {

    private static final long serialVersionUID = 83715083867368_02_037L;

    private final Color color;
    private final FireSettings fireSettings;

    public Wood(Color color, FireSettings fireSettings) {
        this.color = color;
        this.fireSettings = fireSettings;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        tools.getFireTools().affectFlammable(x, y, this, false);
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.testTemperature(x, y);
    }

    @Override
    public final Color getColor() {
        return color;
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
        return 0.05f;
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