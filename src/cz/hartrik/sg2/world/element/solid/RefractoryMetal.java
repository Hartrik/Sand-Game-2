
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.common.random.RatioChance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.temperature.ColorTemperature;

/**
 * Element představující žáruvzdornou slitinu.
 * 
 * @version 2014-09-05
 * @author Patrik Harag
 */
public class RefractoryMetal extends MetalSolid {
    
    private static final long serialVersionUID = 83715083867368_02_072L;
    
    protected final int DENSITY = 10200;
    protected final int MELTING_POINT = 2300;
    protected final float CONDUCTIVE_INDEX = 0.45f;
    protected final Chance CHANCE_TO_DECREMENT = new RatioChance(200);

    public RefractoryMetal(Color color) {
        super(color);
    }

    public RefractoryMetal(Color color, int temperature) {
        super(color, temperature);
    }
    
    // Element

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (temperature > MELTING_POINT)
            world.setAndChange(x, y, new MetalMolten<>(this, DENSITY));
        else
            super.doAction(x, y, tools, world);
    }

    @Override
    public Color getColor() {
        if (temperature > NORMAL_TEMP) {
            int sTemp = (int) (temperature * 1.4f);
            Color fg = ColorTemperature.getInCelsius(sTemp);

            int diff = (temperature - NORMAL_TEMP);
            return (diff >= 850)
                    ? fg
                    : super.getColor().blend(fg.changeAlpha(diff / 1000f));
        } else
            return super.getColor();
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

    // Meltable

    @Override
    public int getMeltingPoint() {
        return MELTING_POINT;
    }

    // Destructible
    
    @Override
    public MetalPowder<RefractoryMetal> destroy() {
        return new MetalPowder<>(this, DENSITY);
    }
    
}