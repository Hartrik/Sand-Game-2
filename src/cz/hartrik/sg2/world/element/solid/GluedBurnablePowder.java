package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.powder.Powder;
import cz.hartrik.sg2.world.element.temperature.Burnable;

/**
 * Tento element obalí jiný práškový element a vytvoří z něj element statický,
 * přičemž zachová jeho hořlavost.
 *
 * @version 2016-06-14
 * @author Patrik Harag
 * @param <E> typ obaleného elementu
 */
public class GluedBurnablePowder<E extends Powder & Burnable>
        extends GluedPowder<E> implements Burnable {

    private static final long serialVersionUID = 83715083867368_02_077L;

    public GluedBurnablePowder(E element) {
        super(element);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        tools.getFireTools().affectFlammable(x, y, this, false);
    }

    // Burnable

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