package cz.hartrik.sg2.world.element.fauna;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.temperature.FireAffected;
import cz.hartrik.sg2.world.element.Metamorphic;

/**
 * Představuje statický element, který napadla bakterie.
 *
 * @version 2016-06-16
 * @author Patrik Harag
 * @param <T>
 */
public class InfectedSolid<T extends Element> extends SolidElement
        implements EBacteria, FireAffected, Metamorphic<T> {

    private static final long serialVersionUID = 83715083867368_02_092L;

    private final T solid;
    private final Bacteria bacteria;

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

    @Override
    public boolean hasTemperature() {
        return solid.hasTemperature();
    }

    @Override
    public boolean isConductive() {
        return solid.isConductive();
    }

    @Override
    public float getConductiveIndex() {
        return solid.getConductiveIndex();
    }

    @Override
    public float loss() {
        return solid.loss();
    }

    // Metamorphic

    @Override
    public T getBasicElement() {
        return solid;
    }

    // FireAffected

    @Override
    public void onFire(int x, int y, Tools tools, World world, float temp) {
        world.setAndChange(x, y, solid);
    }

}