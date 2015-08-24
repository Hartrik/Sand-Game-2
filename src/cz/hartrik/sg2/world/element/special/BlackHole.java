
package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.type.Sourceable;

/**
 * Element představující "černou díru". Pohltí všechny ostatní elementy, které
 * se vyskytnou v její blízkosti.
 * 
 * @version 2015-01-08
 * @author Patrik Harag
 */
public class BlackHole extends Hole implements Sourceable {

    private static final long serialVersionUID = 83715083867368_02_002L;

    public BlackHole(Color color) {
        super(color);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        
//        tools.getDirectionVisitor().visitAll(x, y,
//                (Element element, int eX, int eY) -> {
//                    
//            if (!(element instanceof BlackHole || element instanceof Air))
//                world.setAndChange(eX, eY, world.getBackground());
//        });
        
        // MĚŘENÍ OPTIMALIZACE
        // způsob: naplnění plátna 500x800, testAction = true
        // výsledek: před = 190 c/s
        //             po = 250 c/s + o trochu menší paměťová náročnost
        
        // UP (0, -1)
        
        int eY = y - 1;
        Element element;
        
        if (world.valid(x, eY)) {
            element = world.get(x, eY);
            
            if (!(element instanceof BlackHole || element instanceof Air))
                world.setAndChange(x, eY, world.getBackground());
        }
        
        // DOWN (0,  1)
        
        eY = y + 1;
        
        if (world.valid(x, eY)) {
            element = world.get(x, eY);
            
            if (!(element instanceof BlackHole || element instanceof Air))
                world.setAndChange(x, eY, world.getBackground());
        }
        
        // LEFT (-1,  0)
        
        int eX = x - 1;
        
        if (world.valid(eX, y)) {
            element = world.get(eX, y);
            
            if (!(element instanceof BlackHole || element instanceof Air))
                world.setAndChange(eX, y, world.getBackground());
        }
        
        // RIGHT (1,  0)
        
        eX = x + 1;
        
        if (world.valid(eX, y)) {
            element = world.get(eX, y);
            
            if (!(element instanceof BlackHole || element instanceof Air))
                world.setAndChange(eX, y, world.getBackground());
        }
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getDirectionVisitor().testAll(x, y, element -> {
            return !(element instanceof BlackHole || element instanceof Air);
        });
    }

}