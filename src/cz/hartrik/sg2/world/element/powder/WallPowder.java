
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.solid.Wall;
import cz.hartrik.sg2.world.element.Metamorphic;

/**
 * Element představující rozbitou zeď.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public class WallPowder extends PowderMid
        implements Metamorphic<Wall>, Glueable {

    private static final long serialVersionUID = 83715083867368_02_067L;

    private final Wall wall;

    public WallPowder(Wall wall) {
        super(wall.getColor(), wall.getDensity());
        this.wall = wall;
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.testTemperature(x, y)
                || super.testAction(x, y, tools, world);
    }

    @Override
    public Color getColor(float temperature) {
        return wall.getColor(temperature);
    }
    
    @Override
    public boolean hasTemperature() {
        return true;
    }

    @Override
    public float loss() {
        return 0.0005f;
    }

    @Override
    public boolean isConductive() {
        return true;
    }

    @Override
    public float getConductiveIndex() {
        return 0.1f;
    }

    // Metamorphic

    @Override
    public Wall getBasicElement() {
        return wall;
    }

    // Glueable

    @Override
    public Wall glue() {
        return wall;
    }

}