
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.fluid.FluidDense;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluenced;
import cz.hartrik.sg2.world.element.type.Dryable;

/**
 * Element představující mokrý cement. Pomalu tvrdne, až se z něho stane beton. 
 * Tento mokrý cement se chová jako kapalina.
 * 
 * @version 2014-05-15
 * @author Patrik Harag
 */
public class CementWetFluid extends FluidDense
        implements ThermalInfluenced, Dryable {
    
    private static final long serialVersionUID = 83715083867368_02_005L;

    private final Element next;
    private final Chance chance;
    
    public CementWetFluid(Color color, int density, Chance chance, Element next) {
        super(color, density);
        this.chance = chance;
        this.next = next;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (super.testAction(x, y, tools, world)) {
            final int uY = y + Direction.UP.getY();
            if (world.valid(x, uY)
                    && !(world.get(x, uY) instanceof CementWetFluid)) {
            
                final int dY = y + Direction.DOWN.getY();
                if (world.valid(x, dY)) {
                    if (world.get(x, dY) instanceof Concrete
                            && (chance.nextBoolean() || chance.nextBoolean())) {
                        world.setAndChange(x, y, next);
                        return;
                    }
                } else if (chance.nextBoolean() || chance.nextBoolean()){
                    world.setAndChange(x, y, next);
                    return;
                }
            }
            super.doAction(x, y, tools, world);
        } else {
            if (chance.nextBoolean())
                world.setAndChange(x, y, next);
        }
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return true;
    }

    @Override
    public boolean temperature(int x, int y, Tools tools, World world,
            int temperature, boolean fire) {
        
        if (temperature > 40) { // žár uspíší tuhnutí
            world.setAndChange(x, y, next);
            return true;
        } else return false;
    }

    @Override
    public boolean dry(int x, int y, Tools tools, World world) {
        world.setAndChange(x, y, next);
        return true;
    }
    
}