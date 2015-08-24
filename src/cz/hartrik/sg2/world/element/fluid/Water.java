package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.common.random.RatioChance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import java.io.Serializable;
import java.util.function.IntFunction;

/**
 * @version 2014-12-29
 * @author Patrik Harag
 */
public class Water extends BoilingFluid implements EWater {

    private static final long serialVersionUID = 83715083867368_02_080L;

    static final RatioChance CHANCE_TO_DECREMENT = new RatioChance(200);
    static final float CONDUCTIVE_INDEX = 0.4f;
    
    static final Color STEAM_COLOR = new Color(147, 182, 217);
    static final Chance STEAM_CHANCE = new RatioChance(6);
    
    public Water(Color color, int density) {
        this(color, density, NORMAL_TEMP);
    }
    
    public Water(Color color, int density, int temperature) {
        super(color, density, temperature);
    }

    // Element
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        int temperature = getTemperature();
        
        if (Water.this.vapor(temperature))
            vapor(x, y, tools, world);
        else
            super.doAction(x, y, tools, world);
    }

    protected void vapor(int x, int y, Tools tools, World world) {
        world.setAndChange(x, y,
                new Steam(STEAM_COLOR, -101, STEAM_CHANCE, getTemperature(),
                        (IntFunction<Water> & Serializable)
                        (t) -> new Water(getBaseColor(), getDensity(), t)));
    }
    
    private boolean vapor(int temp) {
        return (temp < 100 ? false
                : (temp > 1500 ? true
                        : RatioChance.nextBoolean((1500 - temp) * 3)));
    }

    public Color getBaseColor() {
        return super.getColor();
    }

    @Override
    public Color getColor() {
        final int temperature = getTemperature();
        double a = 0.;
        
        if (temperature > 90) {
            a = temperature / 3000.;
            if (a > 0.5) a = 0.5;
        }
        
        final Color base = getBaseColor();
        return new Color(base.getDoubleRed(), a, base.getDoubleBlue());
    }
    
    // ThermalConductiveDef
    
    @Override
    public float getConductiveIndex() {
        return CONDUCTIVE_INDEX;
    }

    @Override
    public int decrementTemperature(int value) {
        return CHANCE_TO_DECREMENT.nextBoolean() ? value - 1 : value;
    }

    // Dryable
    
    @Override
    public boolean dry(int x, int y, Tools tools, World world) {
        world.setAndChange(x, y, world.getBackground());
        return true;
    }
    
}