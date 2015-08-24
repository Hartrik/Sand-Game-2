
package cz.hartrik.sg2.process;

import cz.hartrik.common.random.XORShiftRandom;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.factory.FireFactoryImpl;

/**
 * Základní nástroje používané při implementaci chování elementů.
 * 
 * @version 2015-01-07
 * @author Patrik Harag
 */
public class Tools {
    
    protected final World world;
    
    protected final XORShiftRandom xorRandom;
    
    protected final DirectionVisitor directionVisitor;
    protected final PointVisitor pointVisitor;
    protected final GravityTools gravityTools;
    protected final FireTools fireTools;
    
    public Tools(World world) {
        this.world = world;
        this.xorRandom = new XORShiftRandom();
        this.directionVisitor = new DirectionVisitor(world);
        this.pointVisitor = new PointVisitor(world);
        this.gravityTools = new GravityTools(world, this);
        this.fireTools = new FireTools(world, this, new FireFactoryImpl());
    }

    // nástroje
    
    public DirectionVisitor getDirectionVisitor() { return directionVisitor; }
    public PointVisitor getPointVisitor()         { return pointVisitor; }
    public GravityTools getGravityTools() { return gravityTools; }
    public FireTools getFireTools()       { return fireTools; }
    
    // random
    
    public final boolean randomBoolean() {
        return xorRandom.nextBoolean();
    }
    
    public final int randomInt(int max) {
        return xorRandom.nextInt(max);
    }
    
    public final int randomSide() {
        return (xorRandom.nextInt(2) == 0) ? -1 : 1;
    }
    
    public final Direction randomDirection() {
        switch (xorRandom.nextInt(4)) { // switch je zde o 15 % rychl. než pole
            case 0:  return Direction.UP;
            case 1:  return Direction.DOWN;
            case 2:  return Direction.RIGHT;
            default: return Direction.LEFT;
        }
    }
    
    // ostatní
    
    public final boolean valid(final int x, final int y) {
        return world.valid(x, y);
    }

    public final void swap(
            final int x1, final int y1, final Element e1,
            final int x2, final int y2, final Element e2) {
        
        world.setAndChange(x1, y1, e2);
        world.setAndChange(x2, y2, e1);
    }
    
    public final void swap(
            final int x1, final int y1,
            final int x2, final int y2) {
        
        Element temp = world.get(x1, y1);
        world.setAndChange(x1, y1, world.get(x2, y2));
        world.setAndChange(x2, y2, temp);
    }
    
}