
package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.powder.PowderLow;

/**
 * Představuje element, který když se přiblíží k jinému elementu, tak ho pohltí
 * a sám zanikne.
 * 
 * @version 2014-12-28
 * @author Patrik Harag
 */
public class Eliminator extends PowderLow {
    
    private static final long serialVersionUID = 83715083867368_02_010L;

    public Eliminator(Color color) {
        super(color, Integer.MAX_VALUE);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (!eliminate(x, y, tools, world))
            super.doAction(x, y, tools, world);
    }
    
    protected boolean eliminate(int x, int y, Tools tools, World world) {
        return !tools.getDirectionVisitor().visitWhileAll(x, y,
                (Element element, int eX, int eY) -> {
            
            if (!(element instanceof Eliminator || element instanceof Air)) {
                world.setAndChange(eX, eY, world.getBackground());
                world.setAndChange( x,  y, world.getBackground());
                return false;
            } else
                return true;
        });
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return super.testAction(x, y, tools, world)
                || testEliminate(x, y, tools, world);
    }
    
    protected boolean testEliminate(int x, int y, Tools tools, World world) {
        return tools.getDirectionVisitor().testAll(x, y, element -> {
            return !(element instanceof Eliminator || element instanceof Air);
        });
    }
    
    // Glueable
    
    @Override
    public Element glue() {
        return null;
    }
    
}