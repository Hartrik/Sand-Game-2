
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.fluid.FluidHeavy;
import cz.hartrik.sg2.world.element.powder.Powder;
import cz.hartrik.sg2.world.element.temperature.ThermalConductiveDef;
import cz.hartrik.sg2.world.element.type.Meltable;
import cz.hartrik.sg2.world.element.type.Metamorphic;
import cz.hartrik.sg2.world.element.type.Sourceable;

/**
 * Element představující určitý roztavený kov.
 * 
 * @version 2014-12-23
 * @author Patrik Harag
 * @param <E> druh kovu
 */
public class MetalMolten<E extends MetalSolid> extends FluidHeavy
        implements Meltable, ThermalConductiveDef, Metamorphic<E>, Sourceable {
        
    private static final long serialVersionUID = 83715083867368_02_058L;

    protected final E metal;
    
    public MetalMolten(E metal, int density) {
        super(metal.getColor(), density);
        this.metal = metal;
    }

    // Element
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (getTemperature() < metal.getMeltingPoint()
                
                && searchBase(x, y, tools, world)) {
            
//            world.set(x, y, searchBase(x, y, tools, world)
//                    ? metal
//                    : new MetalPowder<>(metal, getDensity()));
            
            world.setAndChange(x, y, metal);
            
        } else {
            affectNear(x, y, tools, world);
            super.doAction(x, y, tools, world);
        }
    }

    protected boolean searchBase(int x, int y, Tools tools, World world) {
        if (y == world.getHeight() - 1) return true;
        return !tools.getDirectionVisitor().visitWhileAll(x, y,
                (element) -> !(element instanceof SolidElement
                        || element instanceof Powder)
        );
    }

    @Override
    public void doParallel(int x, int y, Tools tools, World world) {
        if (tools.randomInt(10) != 0) return;
        
        tools.getDirectionVisitor().visit(x, y, (element) -> {
            
            if (element instanceof MetalMolten) {
                MetalSolid metal2 = ((MetalMolten) element).getBasicElement();
                
                final Color c1 = metal2.getBaseColor();
                final Color c2 = metal.getBaseColor();
                
                int diff = Math.abs(c1.getRed()   - c2.getRed())
                         + Math.abs(c1.getGreen() - c2.getGreen())
                         + Math.abs(c1.getBlue()  - c2.getBlue());
                
                if (diff < 6) return;
                
                Color newColor = c1.changeAlpha(.4).blend(c2.changeAlpha(.4));
                
                metal.setBaseColor(newColor);
                metal2.setBaseColor(newColor);
            }
            
        }, tools.randomDirection());
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
        return true;
    }
    
}