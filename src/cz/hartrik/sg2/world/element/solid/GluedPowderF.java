package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.powder.Powder;
import cz.hartrik.sg2.world.element.temperature.Burnable;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluenced;

/**
 * @version 2014-12-26
 * @author Patrik Harag
 * @param <E>
 */
public class GluedPowderF<E extends Powder & ThermalInfluenced & Burnable>
        extends GluedPowder<E> implements ThermalInfluenced, Burnable {
    
    private static final long serialVersionUID = 83715083867368_02_077L;

    public GluedPowderF(E element) {
        super(element);
    }
    
    @Override
    public boolean temperature(int x, int y, Tools tools, World world,
            int degrees, boolean fire) {
        
        return getBasicElement().temperature(x, y, tools, world, degrees, fire);
    }

    @Override
    public boolean getChanceToBurn() {
        return getBasicElement().getChanceToBurn();
    }

    @Override
    public int getFlammableNumber() {
        return getBasicElement().getFlammableNumber();
    }

    @Override
    public int getDegreeOfFlammability() {
        return getBasicElement().getDegreeOfFlammability();
    }

    @Override
    public boolean getChanceToFlareUp() {
        return getBasicElement().getChanceToFlareUp();
    }

}