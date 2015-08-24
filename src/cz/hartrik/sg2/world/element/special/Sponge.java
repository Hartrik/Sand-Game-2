
package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.type.Dryable;

/**
 * Element představující houbu - pohlcuje elementy typu {@link Dryable}.
 * 
 * @version 2014-05-01
 * @author Patrik Harag
 */
public class Sponge extends Hole {
    
    private static final long serialVersionUID = 83715083867368_02_030L;

    public Sponge(Color color) {
        super(color);
    }
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        tools.getDirectionVisitor().visitAll(x, y,
                (Element element, int eX, int eY) -> {

            if (element instanceof Dryable)
                ((Dryable) element).dry(eX, eY, tools, world);
        });
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getDirectionVisitor()
                .testAll(x, y, element -> element instanceof Dryable);
    }
    
}