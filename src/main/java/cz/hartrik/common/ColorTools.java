
package cz.hartrik.common;

import cz.hartrik.common.reflect.LibraryClass;

/**
 * Obsahuje statické metody pro práci s barvami.
 * 
 * @version 2014-03-27
 * @author Patrik Harag
 */
@LibraryClass
public final class ColorTools {

    private ColorTools() {}
    
    /**
     * Vypočte "průměrnou" barvu z několika barev.
     * 
     * @param colors barvy
     * @return průměrná barva
     */
    public static Color average(Color... colors) {
        int red = 0, blue = 0, green = 0;
        
        for (Color color : colors) {
            red += color.getByteRed();
            blue += color.getByteBlue();
            green += color.getByteGreen();
        }
        
        int count = colors.length;
        return new Color(red / count, green / count, blue / count);
    }
    
}