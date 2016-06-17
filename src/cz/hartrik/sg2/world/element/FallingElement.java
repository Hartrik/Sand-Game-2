
package cz.hartrik.sg2.world.element;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.special.Sourceable;

/**
 * Abstraktní třída pro elementy, na které působí gravitace.
 * 
 * @version 2014-04-24
 * @author Patrik Harag
 */
public abstract class FallingElement extends NonSolidElement
        implements Sourceable {
    
    private static final long serialVersionUID = 83715083867368_02_011L;

    private final Color color;
    private final int density;
    
    public FallingElement(Color color, int density) {
        this.density = density;
        this.color = color;
    }

    @Override public Color getColor() { return color; }
    @Override public int getDensity() { return density; }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        tools.getGravityTools().moveIfValid(x, y, this, x, (y + 1));
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getGravityTools().testMoveAndValid(this, x, (y + 1));
    }
    
}