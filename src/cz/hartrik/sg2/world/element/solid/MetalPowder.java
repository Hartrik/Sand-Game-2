
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.powder.PowderMid;
import cz.hartrik.sg2.world.element.powder.Glueable;
import cz.hartrik.sg2.world.element.Metamorphic;
import cz.hartrik.sg2.world.element.special.Sourceable;

/**
 * Představuje kovový element, v práškovém stavu.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public class MetalPowder extends PowderMid
        implements Meltable, Metamorphic<MetalSolid>, Glueable, Sourceable {

    private static final long serialVersionUID = 83715083867368_02_066L;

    protected final MetalSolid metal;

    MetalPowder(MetalSolid metal) {
        super(metal.getColor(), metal.getDensity());
        this.metal = metal;
    }

    // Element

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (world.getTemperature(x, y) > metal.getMeltingPoint())
            // roztaví se
            world.setAndChange(x, y, metal.melt());
        else
            // bude se dál sypat...
            super.doAction(x, y, tools, world);
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.testTemperature(x, y)
                || super.testAction(x, y, tools, world);
    }

    @Override
    public Color getColor(float temperature) {
        return metal.getColor(temperature);
    }

    @Override
    public boolean hasTemperature() {
        return true;
    }

    @Override
    public boolean isConductive() {
        return true;
    }

    @Override
    public float getConductiveIndex() {
        return metal.getConductiveIndex();
    }

    @Override
    public float loss() {
        return metal.loss();
    }

    // Metamorphic

    @Override
    public MetalSolid getBasicElement() {
        return metal;
    }

    // Glueable

    @Override
    public MetalSolid glue() {
        return metal;
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