
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;

/**
 * Abstraktní třída pro kovy.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public abstract class MetalSolid extends Wall implements Meltable {

    private static final long serialVersionUID = 83715083867368_02_060L;

    private MetalPowder powder;
    private MetalMolten molten;

    public MetalSolid(Color color) {
        super(color);
    }

    abstract MetalSolid newInstance(Color color);

    // Element

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (world.getTemperature(x, y) > getMeltingPoint())
            world.setAndChange(x, y, melt());
    }

    // Meltable

    @Override
    public boolean isMolten() {
        return false;
    }

    public MetalMolten melt() {
        return molten == null ? molten = new MetalMolten(this) : molten;
    }

    // Destructible

    @Override
    public MetalPowder destroy() {
        return powder == null ? powder = new MetalPowder(this) : powder;
    }

}