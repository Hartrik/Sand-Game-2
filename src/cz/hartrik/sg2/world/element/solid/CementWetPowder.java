
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.powder.PowderLow;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluenced;

/**
 * Element představující mokrý cement. Pomalu tvrdne, až se z něho stane beton.
 * Tento mokrý cement se sype podobně jako písek.
 * 
 * @version 2014-05-20
 * @author Patrik Harag
 */
public class CementWetPowder extends PowderLow
        implements ThermalInfluenced {
    
    private static final long serialVersionUID = 83715083867368_02_006L;

    private final Element next;
    private final Chance chance;
    
    public CementWetPowder(Color color, int density, Chance chance, Element next) {
        super(color, density);
        this.chance = chance;
        this.next = next;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (super.testAction(x, y, tools, world))
            super.doAction(x, y, tools, world);
        else
            if (chance.nextBoolean()) world.setAndChange(x, y, next);
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
    
}