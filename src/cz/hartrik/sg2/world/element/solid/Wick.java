
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.FireTools;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.temperature.BurnableDef;
import cz.hartrik.sg2.world.element.temperature.FireSettings;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluenced;

/**
 * Element představující knot.
 * 
 * @version 2014-09-04
 * @author Patrik Harag
 */
public class Wick extends SolidElement
                  implements ThermalInfluenced, BurnableDef {
    
    private static final long serialVersionUID = 83715083867368_02_071L;
    
    private final Color color;
    private final FireSettings fireSettings;

    public Wick(Color color, FireSettings fireSettings) {
        this.color = color;
        this.fireSettings = fireSettings;
    }

    // Element
    
    @Override
    public final Color getColor() {
        return color;
    }

    // BurnableDef
    
    @Override
    public FireSettings getFireSettings() {
        return fireSettings;
    }
    
    // ThermalInfluenced
    
    @Override
    public boolean temperature(int x, int y, Tools tools, World world,
            int degrees, boolean fire) {
        
        if (fire) {  // oheň se jinak šíří dolů jen omezeně
            final FireTools fireTools = tools.getFireTools();
            fireTools.affect(x + 1, y + 1, getFlammableNumber(), true);
            fireTools.affect(x - 1, y + 1, getFlammableNumber(), true);
        }
        
        if (fire && degrees > getDegreeOfFlammability()
                 && getChanceToBurn()) {
            
            world.setAndChange(x, y, world.getBackground());
            return true;
        } else
            return false;
    }
    
}