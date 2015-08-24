
package cz.hartrik.sg2.world.element.gas;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;

/**
 * Abstraktní třída implementující jednoduchý plyn.
 * 
 * @version 2014-04-09
 * @author Patrik Harag
 */
public class SimpleGasElement extends Gas {
    
    private static final long serialVersionUID = 83715083867368_02_046L;

    private final Color color;
    private final int density;
    protected final Chance chanceToMoveUp;

    public SimpleGasElement(Color color, int density, Chance chanceToMoveUp) {
        this.color = color;
        this.density = density;
        this.chanceToMoveUp = chanceToMoveUp;
    }
    
    @Override public Color getColor() { return color; }
    @Override public int getDensity() { return density; }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (!(chanceToMoveUp.nextBoolean()
                && tools.getGravityTools().hoverAuto(x, y, this, x))) {
            
            if (tools.randomBoolean()) {
                if (!tools.getGravityTools().hoverIfValid(x, y, this, x + 1, y))
                     tools.getGravityTools().hoverIfValid(x, y, this, x - 1, y);
            } else {
                if (!tools.getGravityTools().hoverIfValid(x, y, this, x - 1, y))
                     tools.getGravityTools().hoverIfValid(x, y, this, x + 1, y);
            }
        }
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return true;
    }
    
}