
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.element.temperature.TemperatureColor;
import cz.hartrik.sg2.world.factory.FuncMapFactory;
import cz.hartrik.sg2.world.factory.MapFactory;

/**
 * Element představující žáruvzdornou slitinu.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public class RefractoryMetal extends MetalSolid {

    private static final long serialVersionUID = 83715083867368_02_072L;

    static final MapFactory<Color, RefractoryMetal> FACTORY
            = new FuncMapFactory<>(RefractoryMetal::new);

    protected final int DENSITY = 10200;
    protected final int MELTING_POINT = 2300;
    protected final float CONDUCTIVE_INDEX = 0.45f;

    public RefractoryMetal(Color color) {
        super(color);
    }

    @Override
    MetalSolid newInstance(Color color) {
        return FACTORY.apply(color);
    }

    // Element

    @Override
    public Color getColor(float temperature) {
        return TemperatureColor.createColor(color, temperature, 1.4f, 0.001f);
    }

    @Override
    public int getDensity() {
        return DENSITY;
    }

    @Override
    public float getConductiveIndex() {
        return CONDUCTIVE_INDEX;
    }

    @Override
    public float loss() {
        return 0.0001f;
    }

    // Meltable

    @Override
    public int getMeltingPoint() {
        return MELTING_POINT;
    }

}