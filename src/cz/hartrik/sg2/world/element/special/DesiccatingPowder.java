
package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.FallingElement;
import cz.hartrik.sg2.world.element.type.Dryable;

/**
 * Element, který pohlcuje elementy typu {@link Dryable}.
 * 
 * @version 2014-05-01
 * @author Patrik Harag
 */
public class DesiccatingPowder extends FallingElement {
    
    private static final long serialVersionUID = 83715083867368_02_008L;

    public DesiccatingPowder(Color color, int density) {
        super(color, density);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        final int yUnder = y + 1;
        if (world.valid(x, yUnder)) {
            
            final Element element = world.get(x, yUnder);
            if (element instanceof Dryable) {
                ((Dryable) element).dry(x, yUnder, tools, world);
                world.setAndChange(x, y, world.getBackground());
                
            } else if (element instanceof Air) {
                tools.swap(x, y, this, x, yUnder, element);
                
            } else if (!(element instanceof DesiccatingPowder))
                world.setAndChange(x, y, world.getBackground());
        } else
            world.setAndChange(x, y, world.getBackground());
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return true;
    }
    
}