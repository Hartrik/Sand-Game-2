
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.GravityTools;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.powder.Powder;

/**
 * Abstraktní třída pro těžké tekoucí elementy.
 * 
 * @version 2014-05-21
 * @author Patrik Harag
 */
public abstract class FluidHeavy extends FluidDense {
    
    private static final long serialVersionUID = 83715083867368_02_055L;

    public FluidHeavy(Color color, int density) {
        super(color, density);
    }
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (!move(x, y, x, y + 1, tools.getGravityTools(), world))
            flow(x, y, tools.getGravityTools(), world,
                    (tools.randomBoolean() ? -1 : 1));
    }
    
    @Override
    protected boolean flow(int x, int y, GravityTools tools, World world,
            int direction) {
        
        return move(x, y, x + direction, y, tools, world);
    }
    
    private boolean move(int x1, int y1, int x2, int y2, GravityTools tools,
            World world) {
        
        if (world.valid(x2, y2)) { 
            Element element = world.get(x2, y2);
            if (testElement(element, tools)) {
                world.setAndChange(x1, y1, element); 
                world.setAndChange(x2, y2, this);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return testAt(x, y + 1, tools, world)  // dole
            || testAt(x + 1, y, tools, world)  // pravé
            || testAt(x - 1, y, tools, world); // levá
    }
    
    private boolean testAt(int x, int y, Tools tools, World world) {
        return world.valid(x, y)
                && testElement(world.get(x, y), tools.getGravityTools());
    }
    
    private boolean testElement(Element element, GravityTools tools) {
        return !(element instanceof Powder)
                && tools.testMove(this, element);
    }
    
}