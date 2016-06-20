
package cz.hartrik.sg2.process;

import cz.hartrik.common.random.XORShiftRandom;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.factory.FireFactoryImpl;

/**
 * Základní nástroje používané při implementaci chování elementů.
 *
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class Tools {

    protected final World world;

    protected final XORShiftRandom xorRandom;

    protected final DirectionVisitor directionVisitor;
    protected final PointVisitor pointVisitor;
    protected final GravityTools gravityTools;
    protected final FireTools fireTools;

    protected BrushManager brushManager;

    public Tools(World world, BrushManager brushManager) {
        this.world = world;
        this.brushManager = brushManager;

        this.xorRandom = new XORShiftRandom();
        this.directionVisitor = new DirectionVisitor(world);
        this.pointVisitor = new PointVisitor(world);
        this.gravityTools = new GravityTools(world, this);
        this.fireTools = new FireTools(world, this, new FireFactoryImpl());
    }

    public BrushManager getBrushManager() {
        return brushManager;
    }

    // nástroje

    public DirectionVisitor getDirVisitor() { return directionVisitor; }
    public PointVisitor getPointVisitor()   { return pointVisitor; }
    public GravityTools getGravityTools()   { return gravityTools; }
    public FireTools getFireTools()         { return fireTools; }

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
        swapTemperature(x1, y1, x2, y2);
    }

    public final void swap(
            final int x1, final int y1,
            final int x2, final int y2) {

        Element temp = world.get(x1, y1);
        world.setAndChange(x1, y1, world.get(x2, y2));
        world.setAndChange(x2, y2, temp);
        swapTemperature(x1, y1, x2, y2);
    }

    public final void swapTemperature(
            final int x1, final int y1,
            final int x2, final int y2) {

        float t1 = world.getTemperature(x1, y1);
        float t2 = world.getTemperature(x2, y2);
        world.setTemperature(x1, y1, t2);
        world.setTemperature(x2, y2, t1);
    }

    public boolean testTemperature(int x, int y) {
        return world.getTemperature(x, y) != World.DEFAULT_TEMPERATURE;
    }

}