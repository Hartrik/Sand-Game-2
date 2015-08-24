
package cz.hartrik.sg2.app.sandbox.element;

import cz.hartrik.sg2.brush.BrushEffect;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.element.temperature.ThermalChangeable;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluenced;
import java.util.function.Consumer;

/**
 * Štětec poskytující nástroje ke snadnější manipulaci s teplotou elementů.
 * 
 * @version 2015-04-04
 * @author Patrik Harag
 */
public class BrushTemperatureChanger extends BrushEffect {

    @FunctionalInterface
    public static interface FuncAdvanced {
        public boolean apply(ThermalInfluenced element, int x, int y,
                ElementArea area);
    }
    
    // konstruktory
    
    public BrushTemperatureChanger(BrushInfo brushInfo,
            Consumer<ThermalChangeable> function) {
        
        super(brushInfo, (current) -> {
            if (current != null && current instanceof ThermalChangeable)
                function.accept((ThermalChangeable) current);
            
            return current; // aby došlo k updatu chunku
        });
    }

    public BrushTemperatureChanger(BrushInfo brushInfo, FuncAdvanced function) {
        
        super(brushInfo, (current, x, y, world, controls) -> {
            if (current != null && current instanceof ThermalInfluenced)
                return function.apply((ThermalInfluenced) current, x, y, world)
                        ? null  // aby nedošlo k překreslení právě vloženého elementu
                        : current;
            
            return current; // aby došlo k updatu chunku
        });
    }
    
}