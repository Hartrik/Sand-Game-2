package cz.hartrik.sg2.world;

import cz.hartrik.common.Pair;
import cz.hartrik.common.Point;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 
 * @version 2015-03-28
 * @author Patrik Harag
 */
public interface Region extends Iterable<Element> {
    
    // iterátory
    
    @Override
    public Iterator<Element> iterator();
    
    public Iterator<Point> iteratorPoint();

    public Iterator<Pair<Element, Point>> iteratorLabeled();
    
    // streamy
    
    public Stream<Element> stream();
    
    public Stream<Point> streamPoint();
    
    public Stream<Pair<Element, Point>> streamLabeled();
    
    // for each
    
    @Override
    public void forEach(Consumer<? super Element> consumer);
    
    public void forEach(PointElementConsumer<? super Element> consumer);
    
    public void forEachPoint(PointConsumer consumer);
    
    // nástroje
    
    public RegionTools getTools();
    
}