
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.common.random.RatioChance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.gas.GasTC;
import java.util.function.IntFunction;

/**
 * @version 2014-12-28
 * @author Patrik Harag
 */
public class Steam extends GasTC {
    
    private static final long serialVersionUID = 83715083867368_02_081L;
    
    protected static final RatioChance CHANCE_DECREMENT = new RatioChance(10);
    protected static final RatioChance CHANCE_RND = new RatioChance(1000);
    protected static final float CONDUCTIVE_INDEX = 0.5f;
    
    private final IntFunction<Water> waterSupp;
    
    public Steam(Color color, int density, Chance chanceToMoveUp,
            IntFunction<Water> waterSupp) {
        
        this(color, density, chanceToMoveUp, NORMAL_TEMP, waterSupp);
    }

    public Steam(Color color, int density, Chance chanceToMoveUp,
            int temperature, IntFunction<Water> waterSupp) {
        
        super(color, density, chanceToMoveUp, temperature);
        this.waterSupp = waterSupp;
    }

    // Element
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        final int temperature = getTemperature();
        
        if (temperature < 25)
            world.setAndChange(x, y, waterSupp.apply(temperature));
        else if (CHANCE_RND.nextBoolean())
            world.setAndChange(x, y, waterSupp.apply(temperature / 2));
        else
            super.doAction(x, y, tools, world);
    }

    //  ThermalConductiveDef

    @Override
    public float getConductiveIndex() {
        return CONDUCTIVE_INDEX;
    }

    @Override
    public int decrementTemperature(int value) {
        return (CHANCE_DECREMENT.nextBoolean()) ? value - 1 : value;
    }
    
}