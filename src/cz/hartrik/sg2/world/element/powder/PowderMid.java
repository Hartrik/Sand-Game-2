
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.GravityTools;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.World;

/**
 * Abstraktní třída pro elementy práškového / písčitého typu. <p>
 * 
 * Znázornění rozsypávání:
 * <pre>
 *   #
 *  ###
 * #####
 * </pre>
 * 
 * @version 2014-04-07
 * @author Patrik Harag
 */
public class PowderMid extends Powder {
    
    private static final long serialVersionUID = 83715083867368_02_024L;

    public PowderMid(Color color, int density) {
        super(color, density);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        final int yUnder = y + 1;
        final GravityTools gTools = tools.getGravityTools();
        
        if (gTools.moveIfValid(x, y, this, x, yUnder)) return;
        
        final boolean canRight = world.valid(x + 1, yUnder);
        final boolean canLeft  = world.valid(x - 1, yUnder);

        if (canLeft && canRight) {
            if (tools.randomBoolean()) {
                if (!gTools.move(x, y, this, x - 1, yUnder))
                     gTools.move(x, y, this, x + 1, yUnder);
            } else {
                if (!gTools.move(x, y, this, x + 1, yUnder))
                     gTools.move(x, y, this, x - 1, yUnder);
            }
        } else if (canLeft)  gTools.move(x, y, this, x - 1, yUnder);
          else if (canRight) gTools.move(x, y, this, x + 1, yUnder);
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        final int yUnder = y + 1;
        
        return tools.getGravityTools().testMoveAndValid(this, x, yUnder)
            || (world.valid(x + 1, yUnder)
                && tools.getGravityTools().testMove(this, x + 1, yUnder))
            || (world.valid(x - 1, yUnder)
                && tools.getGravityTools().testMove(this, x - 1, yUnder));
    }
    
}