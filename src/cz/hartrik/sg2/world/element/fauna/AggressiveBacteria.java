package cz.hartrik.sg2.world.element.fauna;

import cz.hartrik.common.Color;
import cz.hartrik.common.Counter;
import cz.hartrik.common.random.RatioChance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.temperature.ThermalConductive;
import cz.hartrik.sg2.world.element.type.Metamorphic;

/**
 * @version 2014-12-22
 * @author Patrik Harag
 */
public class AggressiveBacteria extends Bacteria {

    private static final long serialVersionUID = 83715083867368_02_090L;
    
    public AggressiveBacteria(Color color, int maxDegrees) {
        super(color, maxDegrees);
    }

    @Override
    public <T extends Metamorphic<?> & ThermalConductive> boolean live(
            int x, int y, Tools tools, World world, T wrapper) {
        
        if (wrapper.getTemperature() >= getMaxDegrees()) {
            world.setAndChange(x, y, wrapper.getBasicElement());
            return false;
        }
        
        final Counter bacCounter = new Counter();
        final Counter airCounter = new Counter();
        tools.getDirectionVisitor().visit(x, y, (element) -> {
            if (element instanceof EBacteria)
                bacCounter.increase();
            else if (element instanceof Air)
                airCounter.increase();
        }, Direction.values());
        
        diffuse(x, y, tools, world);
        
        // new
        
        if (airCounter.getValue() > 0 && RatioChance.nextBoolean(1000)) {
            world.setAndChange(x, y, world.getBackground());
            return false;
        }
        
        //
        
        int count =  bacCounter.getValue();
        if (count < 2 || count > 3) {
            world.setAndChange(x, y, wrapper.getBasicElement());
            return false;
        }
        return true;
    }
    
}