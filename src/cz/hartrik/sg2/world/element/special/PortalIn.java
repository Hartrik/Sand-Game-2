package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.NonSolidElement;
import java.util.function.Supplier;

/**
 * Element představující vstupní portál.
 * 
 * @version 2015-02-16
 * @author Patrik Harag
 */
public class PortalIn extends Portal {

    private static final long serialVersionUID = 83715083867368_02_088L;
    
    public PortalIn(Supplier<Color> supplier) {
        super(supplier);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        final Direction direction = tools.randomDirection();
        
        final int ex = x + direction.getX();
        final int ey = y + direction.getY();
        
        if (world.valid(ex, ey)) {
            final Element element = world.get(ex, ey);

            if (!(element instanceof NonSolidElement) || element instanceof Air)
                return;

            final int dx = -direction.getX();
            final int dy = -direction.getY();
            int ix = x + dx;
            int iy = y + dy;

            boolean out = false;

            while (world.valid(ix, iy)) {
                final Element next = world.get(ix, iy);

                if (out) {
                    if (next instanceof Air) {
                        world.setAndChange(ix, iy, element);
                        world.set(ex, ey, world.getBackground());
                        return;
                    } else if (!(next instanceof PortalOut)) {
                        // přes tento výstup nelze projít
                        out = false;
                    }
                } else if (next instanceof PortalOut) {
                    // nalezen výstup
                    out = true;
                }

                ix += dx;
                iy += dy;
            }
        }
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getDirectionVisitor().testAll(x, y, (element) ->
                element instanceof NonSolidElement && !(element instanceof Air));
    }
    
}