package cz.hartrik.sg2.world.element.fauna;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.temperature.ThermalConductiveDef;
import cz.hartrik.sg2.world.element.type.Metamorphic;
import cz.hartrik.sg2.world.element.type.Organic;

import static cz.hartrik.sg2.world.element.temperature.Thermal.NORMAL_TEMP;

/**
 * @version 2014-12-22
 * @author Patrik Harag
 * @param <T>
 */
public class InfectedSolid<T extends Element> extends SolidElement
        implements Organic, ThermalConductiveDef, Metamorphic<T>, EBacteria {
    
    private static final long serialVersionUID = 83715083867368_02_092L;
    
    private final T solid;
    private final Bacteria bacteria;
    private int temp = NORMAL_TEMP;

    public InfectedSolid(T solid, Bacteria bacteria) {
        this.solid = solid;
        this.bacteria = bacteria;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        bacteria.live(x, y, tools, world, this);
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return true;
    }
    
    @Override
    public Color getColor() {
        return solid.getColor().blend(bacteria.getColor());
    }

    // ThermalInfluenced
    
    @Override
    public boolean temperature(int x, int y, Tools tools, World world,
            int degrees, boolean fire) {
        
        if (fire || degrees >= bacteria.getMaxDegrees()) {
            world.setAndChange(x, y, solid);
            return true;
        }
        
        return false;
    }

    // Metamorphic
    
    @Override
    public T getBasicElement() {
        return solid;
    }
    
    // ThermalConductiveDef

    @Override
    public int getTemperature() {
        return temp;
    }

    @Override
    public void setTemperature(int temperature) {
        temp = temperature;
    }

    @Override
    public float getConductiveIndex() {
        return 0.5f;
    }
    
}