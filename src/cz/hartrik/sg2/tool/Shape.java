
package cz.hartrik.sg2.tool;

import cz.hartrik.common.Point;
import cz.hartrik.sg2.world.BrushInserter;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Rozhraní pro nástroje kreslící nějaký tvar.
 * 
 * @version 2015-03-19
 * @author Patrik Harag
 */
public interface Shape extends Tool, Draggable, Fillable, Iterable<Point> {

    @Override
    public void apply(int mX, int mY, BrushInserter<?> inserter);

    @Override
    public void apply(int mX, int mY, int width, int height, BrushInserter<?> inserter);
    
    // iterátory
    
    public Iterator<Point> iterator(int x, int y, int width, int height);
    
    public Iterator<Point> iterator(int x, int y);
    
    @Override
    public Iterator<Point> iterator();
    
    // streamy
    
    public Stream<Point> stream(int x, int y, int width, int height);
    
    public Stream<Point> stream(int x, int y);
    
    public Stream<Point> stream();
    
}