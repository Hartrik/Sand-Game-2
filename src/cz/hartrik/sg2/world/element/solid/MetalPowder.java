
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.powder.PowderMidTC;
import cz.hartrik.sg2.world.element.temperature.ThermalConductiveDef;
import cz.hartrik.sg2.world.element.type.Glueable;
import cz.hartrik.sg2.world.element.type.Meltable;
import cz.hartrik.sg2.world.element.type.Metamorphic;
import cz.hartrik.sg2.world.element.type.Sourceable;

/**
 * Představuje kovový element, v práškovém stavu.
 * 
 * @version 2014-08-31
 * @author Patrik Harag
 * @param <E> typ kovu
 */
public class MetalPowder<E extends MetalSolid> extends PowderMidTC
        implements Meltable, ThermalConductiveDef, Metamorphic<E>, Glueable,
                   Sourceable {
        
    private static final long serialVersionUID = 83715083867368_02_066L;

    protected final E metal;
    
    public MetalPowder(E metal, int density) {
        super(metal.getColor(), density);
        this.metal = metal;
    }

    // Element
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (metal.getTemperature() > metal.getMeltingPoint())
            world.setAndChange(x, y, new MetalMolten<>(metal, getDensity()));
        else
            super.doAction(x, y, tools, world);
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return getTemperature() > NORMAL_TEMP
                || super.testAction(x, y, tools, world);
    }
    
    @Override
    public Color getColor() {
        return metal.getColor();
    }
    
    // Metamorphic
    
    @Override
    public E getBasicElement() {
        return metal;
    }
    
    // Glueable
    
    @Override
    public E glue() {
        return metal;
    }
    
    // ThermalConductiveDef
    
    @Override
    public void setTemperature(int temperature) {
        metal.setTemperature(temperature);
    }

    @Override
    public int getTemperature() {
        return metal.getTemperature();
    }

    @Override
    public float getConductiveIndex() {
        return metal.getConductiveIndex();
    }

    @Override
    public int decrementTemperature(int value) {
        return metal.decrementTemperature(value);
    }

    // Meltable
    
    @Override
    public int getMeltingPoint() {
        return metal.getMeltingPoint();
    }

    @Override
    public final boolean isMolten() {
        return false;
    }
    
}