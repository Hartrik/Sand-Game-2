package cz.hartrik.sg2.world.element.fauna;

import cz.hartrik.common.Color;
import cz.hartrik.common.Counter;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.temperature.ThermalConductive;
import cz.hartrik.sg2.world.element.type.Metamorphic;
import cz.hartrik.sg2.world.element.type.Organic;
import java.io.Serializable;

/**
 * @version 2014-12-22
 * @author Patrik Harag
 */
public class Bacteria implements Serializable {
    
    private static final long serialVersionUID = 83715083867368_02_091L;
    
    private final Color color;
    private final int maxDegrees;

    public Bacteria(Color color, int maxDegrees) {
        this.color = color;
        this.maxDegrees = maxDegrees;
    }
    
    public <T extends Metamorphic<?> & ThermalConductive> boolean live(
            int x, int y, Tools tools, World world, T wrapper) {
        
        if (wrapper.getTemperature() >= getMaxDegrees()) {
            world.setAndChange(x, y, wrapper.getBasicElement());
            return false;
        }
        
        final int count = countAt(x, y, tools, world);
        
        diffuse(x, y, tools, world);
        
        if (count < 2 || count > 3) {
            world.setAndChange(x, y, wrapper.getBasicElement());
            return false;
        }
        return true;
    }

    protected int countAt(int x, int y, Tools tools, World world) {
        final Counter counter = new Counter();
        tools.getDirectionVisitor().visit(x, y, (element) -> {
            if (element instanceof EBacteria)
                counter.increase();
        }, Direction.values());
        
        return counter.getValue();
    }
    
    public void diffuse(int x, int y, Tools tools, World world) {
        tools.getDirectionVisitor().visit(x, y, (e, ix, iy) -> {
            if (e instanceof Organic && !(e instanceof EBacteria)
                    && countAt(ix, iy, tools, world) == 3) {
                
                world.setAndChange(ix, iy, new InfectedSolid<>(e, this));
            }
        }, tools.randomDirection(), tools.randomDirection());
    }
    
    public Color getColor() {
        return color;
    }

    public int getMaxDegrees() {
        return maxDegrees;
    }
    
}