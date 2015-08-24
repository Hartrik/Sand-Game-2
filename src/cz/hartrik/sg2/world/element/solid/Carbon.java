package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.sg2.world.element.powder.WallPowderTC;
import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.common.random.RatioChance;
import cz.hartrik.sg2.world.element.temperature.ColorTemperature;

/**
 * Element představující uhlík.
 * 
 * @version 2014-09-11
 * @author Patrik Harag
 */
public class Carbon extends WallTC {

    private static final long serialVersionUID = 83715083867368_02_076L;
    
    protected static final int DENSITY = 1750;
    protected static final int MELTING_POINT = 3000;  // nepoužité
    protected static final float CONDUCTIVE_INDEX = 0.1f;
    protected static final Chance CHANCE_TO_DECREMENT = new RatioChance(20);
    
    public Carbon(Color color) {
        super(color);
    }

    public Carbon(Color color, int temperature) {
        super(color, temperature);
    }

    // Element
    
    @Override
    public int getDensity() {
        return DENSITY;
    }
    
    @Override
    public Color getColor() {
        if (temperature > NORMAL_TEMP) {
            final int sTemp = temperature / 2;
            final Color fg = ColorTemperature.getInCelsius(sTemp);
            final int diff = (temperature - NORMAL_TEMP) / 2;

            return (diff >= 900)
                    ? fg
                    : super.getColor().blend(fg.changeAlpha(diff / 1000f));
        } else {
            return super.getColor();
        }
    }
    
    // ThermalConductiveDef
    
    @Override
    public float getConductiveIndex() {
        return CONDUCTIVE_INDEX;
    }

    @Override
    public int decrementTemperature(int value) {
        return CHANCE_TO_DECREMENT.nextBoolean() ? --value : value;
    }

    // Destroyable
    
    @Override
    public WallPowderTC<Carbon> destroy() {
        return new WallPowderTC<>(this, DENSITY);
    }
    
}