package cz.hartrik.sg2.world.module;

import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ModularWorld;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.NonSolidElement;

/**
 * Nestatické elementy budou při dopadu na dno přeneseny opět nahoru.
 * 
 * @version 2015-03-07
 * @author Patrik Harag
 */
public class ModuleNoBottom implements WorldModule<ModularWorld> {

    @Override
    public final boolean nextCycle(ModularWorld area) {
        return refresh(area);
    }

    @Override
    public boolean refresh(ModularWorld area) {
        final int y = area.getHeight() - 1;
        
        for (int x = 0; x < area.getWidth(); x++) {
            
            final Element bottom = area.get(x, y);
            if (bottom instanceof NonSolidElement && !(bottom instanceof Air)) {
                
                final Element top = area.get(x, 0);
                if (top == null || top instanceof Air) {
                    area.setAndChange(x, 0, bottom);
                    area.setAndChange(x, y, top);
                }
            }
        }
        return true;
    }
    
}