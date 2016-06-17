
package cz.hartrik.sg2.world.element.temperature;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.World;

/**
 * Třída poskytující barevnou teplotu / teplotu chromatičnosti.
 * http://en.wikipedia.org/wiki/Color_temperature
 *
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class TemperatureColor {

    private TemperatureColor() { }
    
    public static Color getInCelsius(int celsius) {
        return getInKelvin(celsius + 273);
    }

    public static Color getInKelvin(int kelvin) {
        return COLORS[countIndex(kelvin)];
    }

    protected static int countIndex(int kelvin) {
        if (kelvin < 1000) return 0;
        if (kelvin > 10100) return COLORS.length - 1;

        return (kelvin / 100) - 10;
    }

    public static Color createColor(Color baseColor, float temperature,
            float tFactor, float aFactor) {

        if (temperature > World.DEFAULT_TEMPERATURE) {
            final float sTemp = temperature * tFactor;
            final Color fg = TemperatureColor.getInCelsius((int) sTemp);

            float alpha = sTemp * aFactor;
            if (alpha > 0.99f)
                return fg;
            else
                return baseColor.blend(fg.changeAlpha(sTemp * aFactor));
        }
        return baseColor;
    }

    public static final Color[] COLORS = {
        new Color(255,  51,   0), // 1000 K
        new Color(255,  69,   0), // 1100 K
        new Color(255,  82,   0), // 1200 K
        new Color(255,  93,   0), // 1300 K
        new Color(255, 102,   0), // 1400 K
        new Color(255, 111,   0), // 1500 K
        new Color(255, 118,   0), // 1600 K
        new Color(255, 124,   0), // 1700 K
        new Color(255, 130,   0), // 1800 K
        new Color(255, 135,   0), // 1900 K
        new Color(255, 141,  11), // 2000 K
        new Color(255, 146,  29), // 2100 K
        new Color(255, 152,  41), // 2200 K
        new Color(255, 157,  51), // 2300 K
        new Color(255, 162,  60), // 2400 K
        new Color(255, 166,  69), // 2500 K
        new Color(255, 170,  77), // 2600 K
        new Color(255, 174,  84), // 2700 K
        new Color(255, 178,  91), // 2800 K
        new Color(255, 182,  98), // 2900 K
        new Color(255, 185, 105), // 3000 K
        new Color(255, 189, 111), // 3100 K
        new Color(255, 192, 118), // 3200 K
        new Color(255, 195, 124), // 3300 K
        new Color(255, 198, 130), // 3400 K
        new Color(255, 201, 135), // 3500 K
        new Color(255, 203, 141), // 3600 K
        new Color(255, 206, 146), // 3700 K
        new Color(255, 208, 151), // 3800 K
        new Color(255, 211, 156), // 3900 K
        new Color(255, 213, 161), // 4000 K
        new Color(255, 215, 166), // 4100 K
        new Color(255, 217, 171), // 4200 K
        new Color(255, 219, 175), // 4300 K
        new Color(255, 221, 180), // 4400 K
        new Color(255, 223, 184), // 4500 K
        new Color(255, 225, 188), // 4600 K
        new Color(255, 226, 192), // 4700 K
        new Color(255, 228, 196), // 4800 K
        new Color(255, 229, 200), // 4900 K
        new Color(255, 231, 204), // 5000 K
        new Color(255, 232, 208), // 5100 K
        new Color(255, 234, 211), // 5200 K
        new Color(255, 235, 215), // 5300 K
        new Color(255, 237, 218), // 5400 K
        new Color(255, 238, 222), // 5500 K
        new Color(255, 239, 225), // 5600 K
        new Color(255, 240, 228), // 5700 K
        new Color(255, 241, 231), // 5800 K
        new Color(255, 243, 234), // 5900 K
        new Color(255, 244, 237), // 6000 K
        new Color(255, 245, 240), // 6100 K
        new Color(255, 246, 243), // 6200 K
        new Color(255, 247, 245), // 6300 K
        new Color(255, 248, 248), // 6400 K
        new Color(255, 249, 251), // 6500 K
        new Color(255, 249, 253), // 6600 K
        new Color(254, 250, 255), // 6700 K
        new Color(252, 248, 255), // 6800 K
        new Color(250, 247, 255), // 6900 K
        new Color(247, 245, 255), // 7000 K
        new Color(245, 244, 255), // 7100 K
        new Color(243, 243, 255), // 7200 K
        new Color(241, 241, 255), // 7300 K
        new Color(239, 240, 255), // 7400 K
        new Color(238, 239, 255), // 7500 K
        new Color(236, 238, 255), // 7600 K
        new Color(234, 237, 255), // 7700 K
        new Color(233, 236, 255), // 7800 K
        new Color(231, 234, 255), // 7900 K
        new Color(229, 233, 255), // 8000 K
        new Color(228, 233, 255), // 8100 K
        new Color(227, 232, 255), // 8200 K
        new Color(225, 231, 255), // 8300 K
        new Color(224, 230, 255), // 8400 K
        new Color(223, 229, 255), // 8500 K
        new Color(221, 228, 255), // 8600 K
        new Color(220, 227, 255), // 8700 K
        new Color(219, 226, 255), // 8800 K
        new Color(218, 226, 255), // 8900 K
        new Color(217, 225, 255), // 9000 K
        new Color(216, 224, 255), // 9100 K
        new Color(215, 223, 255), // 9200 K
        new Color(214, 223, 255), // 9300 K
        new Color(213, 222, 255), // 9400 K
        new Color(212, 221, 255), // 9500 K
        new Color(211, 221, 255), // 9600 K
        new Color(210, 220, 255), // 9700 K
        new Color(209, 220, 255), // 9800 K
        new Color(208, 219, 255), // 9900 K
        new Color(207, 218, 255)  // 10000 K
    };

}