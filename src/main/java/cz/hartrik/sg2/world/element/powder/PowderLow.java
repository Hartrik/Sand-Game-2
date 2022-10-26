package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.GravityTools;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.NonSolidElement;

/**
 * Abstraktní třída pro elementy práškového / písčitého typu, které se
 * rozsypávají do větší šířky.
 * 
 * <pre>
 *     #
 *   #####
 * #########
 * </pre>
 * 
 * @version 2014-04-20
 * @author Patrik Harag
 */
public class PowderLow extends Powder {
    
    private static final long serialVersionUID = 83715083867368_02_025L;

    public PowderLow(Color color, int density) {
        super(color, density);
    }

    // zde je nejdůležitější rychlost
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        final int yUnder = y + 1;
        final GravityTools gTools = tools.getGravityTools();
        
        if (gTools.moveIfValid(x, y, this, x, yUnder)) return;
        
        final boolean canRight = world.valid(x + 1, yUnder)
                && world.get(x + 1, yUnder) instanceof NonSolidElement;
        final boolean canLeft  = world.valid(x - 1, yUnder)
                && world.get(x - 1, yUnder) instanceof NonSolidElement;

        if (canLeft && canRight) {
            final boolean canRightDouble = world.valid(x + 2, yUnder);
            final boolean canLeftDouble  = world.valid(x - 2, yUnder);
            
            if (canLeftDouble && canRightDouble) {
                if (tools.randomBoolean()) {
                    if (!gTools.move(x, y, this, x - 2, yUnder))
                        if(!gTools.move(x, y, this, x + 2, yUnder))
                            if (!gTools.move(x, y, this, x - 1, yUnder))
                                 gTools.move(x, y, this, x + 1, yUnder);
                } else {
                    if (!gTools.move(x, y, this, x + 2, yUnder))
                        if(!gTools.move(x, y, this, x - 2, yUnder))
                            if (!gTools.move(x, y, this, x + 1, yUnder))
                                 gTools.move(x, y, this, x - 1, yUnder);
                }
            } else if (canLeftDouble) {
                gTools.move(x, y, this, x - 2, yUnder);
            } else if (canRightDouble) {
                gTools.move(x, y, this, x + 2, yUnder);
            } else {
                if (tools.randomBoolean()) {
                    if (!gTools.move(x, y, this, x - 1, yUnder))
                         gTools.move(x, y, this, x + 1, yUnder);
                } else {
                    if (!gTools.move(x, y, this, x + 1, yUnder))
                         gTools.move(x, y, this, x - 1, yUnder);
                }
            }
        } else if (canLeft) {
            if (world.valid(x - 2, yUnder))
                gTools.move(x, y, this, x - 2, yUnder);
            else
                gTools.move(x, y, this, x - 1, yUnder);
        } else if (canRight) {
            if (world.valid(x + 2, yUnder))
                gTools.move(x, y, this, x + 2, yUnder);
            else
                gTools.move(x, y, this, x + 1, yUnder);
        }
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getGravityTools().testMoveAndValid(this, x, (y + 1))
                || (world.valid(x + 1, y + 1)
                    && testSide(x, y + 1,  1, tools, world))
                || (world.valid(x - 1, y + 1)
                    && testSide(x, y + 1, -1, tools, world));
    }
    
    private boolean testSide(int x, int yUnder, int side,
            Tools tools, World world) {

        final int x1 = x + side;
        if (!tools.valid(x1, yUnder)) return false;
        final Element element1 = world.get(x1, yUnder);
        
        return element1 instanceof NonSolidElement
            && (tools.getGravityTools().testMove(this, element1)
                || (tools.valid(x1 + side, yUnder)
                    && tools.getGravityTools().testMove(this, x1 + side, yUnder)));
    }
    
}