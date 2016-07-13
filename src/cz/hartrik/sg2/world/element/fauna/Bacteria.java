package cz.hartrik.sg2.world.element.fauna;

import cz.hartrik.common.Color;
import cz.hartrik.common.Counter;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Metamorphic;
import cz.hartrik.sg2.world.element.Organic;
import java.io.Serializable;

/**
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class Bacteria implements Serializable {

    private static final long serialVersionUID = 83715083867368_02_091L;

    private final Color color;
    private final int maxTemperature;

    public Bacteria(Color color, int maxTemperature) {
        this.color = color;
        this.maxTemperature = maxTemperature;
    }

    public <T extends Metamorphic<?>> boolean live(
            int x, int y, Tools tools, World world, T wrapper) {

        if (world.getTemperature(x, y) >= getMaxTemperature()) {
            world.setAndChange(x, y, wrapper.getBasicElement());
            return false;
        }

        final int count = countBacteriasAround(x, y, tools, world);

        diffuse(x, y, tools, world);

        if (count < 2 || count > 3) {
            world.setAndChange(x, y, wrapper.getBasicElement());
            return false;
        }
        return true;
    }

    protected int countBacteriasAround(int x, int y, Tools tools, World world) {
        final Counter counter = new Counter();
        tools.getDirVisitor().visit(x, y, (element) -> {
            if (element instanceof EBacteria)
                counter.increase();
        }, Direction.values());

        return counter.getValue();
    }

    public void diffuse(int x, int y, Tools tools, World world) {
        tools.getDirVisitor().visit(x, y, (e, ix, iy) -> {
            if (e instanceof Organic && !(e instanceof EBacteria)
                    && countBacteriasAround(ix, iy, tools, world) == 3) {

                world.setAndChange(ix, iy, new InfectedSolid<>(e, this));
            }
        }, tools.randomDirection(), tools.randomDirection());
    }

    public Color getColor() {
        return color;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

}