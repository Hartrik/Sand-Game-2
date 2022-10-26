
package cz.hartrik.sg2.brush;

import cz.hartrik.sg2.brush.manage.BrushInfo;

/**
 * Štětec nastavující teplotu.
 *
 * @version 2016-06-13
 * @author Patrik Harag
 */
public class BrushTemperature extends BrushEffect {

    public BrushTemperature(BrushInfo brushInfo, int temperature) {
        super(brushInfo, (element, x, y, world, controls) -> {
            if (element != null && element.hasTemperature()) {
                world.setTemperature(x, y, temperature);

                // vrací aktuální element, aby došlo k updatu chunku
                return element;
            }

            // nic se neprovede
            return null;
        });
    }

}