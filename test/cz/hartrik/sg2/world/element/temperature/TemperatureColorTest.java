
package cz.hartrik.sg2.world.element.temperature;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Patrik Harag
 */
public class TemperatureColorTest {

    @Test
    public void testCountIndex() {
        assertEquals(0, TemperatureColor.countIndex(500));
        assertEquals(0, TemperatureColor.countIndex(999));

        assertEquals(0, TemperatureColor.countIndex(1000));
        assertEquals(0, TemperatureColor.countIndex(1050));

        assertEquals(1, TemperatureColor.countIndex(1100));
        assertEquals(1, TemperatureColor.countIndex(1150));

        assertEquals(2, TemperatureColor.countIndex(1200));
        assertEquals(3, TemperatureColor.countIndex(1300));
        assertEquals(4, TemperatureColor.countIndex(1400));

        int index = TemperatureColor.countIndex(10500);
        assertTrue(TemperatureColor.COLORS[TemperatureColor.COLORS.length - 1]
                == TemperatureColor.COLORS[index]);
    }

}