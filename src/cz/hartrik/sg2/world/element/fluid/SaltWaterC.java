
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.random.Chance;
import java.util.function.Supplier;

/**
 * Představuje slanou vodu s proměnlivou barvou - rychle se střídající podobné
 * barvy trochu vyruší statický efekt.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public class SaltWaterC extends SaltWater {

    private static final long serialVersionUID = 83715083867368_02_070L;

    private final Supplier<Color> colorSupplier;

    public SaltWaterC(Supplier<Color> supplier, int density, Chance chance) {
        super(Color.BLACK, density, chance);

        this.colorSupplier = supplier;
    }

    @Override
    public Color getColor() {
        return colorSupplier.get();
    }

    public Supplier<Color> getColorSupplier() {
        return colorSupplier;
    }

}