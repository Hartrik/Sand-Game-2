
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.solid.Wall;
import cz.hartrik.sg2.world.element.temperature.ThermalConductive;
import cz.hartrik.sg2.world.element.temperature.ThermalConductiveDef;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluenced;
import cz.hartrik.sg2.world.element.type.Glueable;
import cz.hartrik.sg2.world.element.type.Metamorphic;

/**
 * Element představující rozbitou zeď šířící teplo.
 * 
 * @version 2014-09-11
 * @author Patrik Harag
 * @param <E> typ zdi
 */
public class WallPowderTC<E extends Wall & ThermalConductiveDef>
        extends PowderMid implements ThermalConductive, ThermalInfluenced,
                                     Metamorphic<E>, Glueable {
    
    private static final long serialVersionUID = 83715083867368_02_075L;
    
    private final E wall;
    
    public WallPowderTC(E wall, int density) {
        super(null, density);
        this.wall = wall;
    }

    // Element
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        wall.doAction(x, y, tools, world);  // šíření tepla
        super.doAction(x, y, tools, world); // pohyb
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return wall.testAction(x, y, tools, world)  // šíření tepla
                || super.testAction(x, y, tools, world);  // pohyb
    }
    
    @Override
    public Color getColor() {
        return wall.getColor();
    }
    
    // Metamorphic
    
    @Override
    public E getBasicElement() {
        return wall;
    }

    // Glueable
    
    @Override
    public E glue() {
        return wall;
    }
    
    // ThermalConductive

    @Override
    public int getTemperature() {
        return wall.getTemperature();
    }

    @Override
    public void setTemperature(int temperature) {
        wall.setTemperature(temperature);
    }

    @Override
    public int conductTemperature(int temperature) {
        return wall.conductTemperature(temperature);
    }
    
    // ThermalInfluenced
    
    @Override
    public boolean temperature(int x, int y, Tools tools, World world,
            int degrees, boolean fire) {
        
        return wall.temperature(x, y, tools, world, degrees, fire);
    }
    
}