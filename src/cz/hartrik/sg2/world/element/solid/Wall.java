
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.powder.Powder;
import cz.hartrik.sg2.world.element.powder.WallPowder;
import cz.hartrik.sg2.world.element.temperature.TemperatureColor;

/**
 * Abstraktní element představující stěnu/zeď.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public abstract class Wall extends SolidElement implements Destructible {

    private static final long serialVersionUID = 83715083867368_02_033L;

    protected final Color color;

    private Powder powder;

    public Wall(Color color) {
        this.color = color;
    }

    // Element

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.testTemperature(x, y);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Color getColor(float temperature) {
        return TemperatureColor.createColor(color, temperature, 0.5f, 0.001f);
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
        return 0.005f;
    }

    @Override
    public float loss() {
        return 0.001f;
    }

    // Destructible

    @Override
    public Powder destroy() {
        if (powder == null)
            powder = new WallPowder(this);

        return powder;
    }

}