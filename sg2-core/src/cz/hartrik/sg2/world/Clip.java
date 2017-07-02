package cz.hartrik.sg2.world;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Iterators;
import cz.hartrik.common.Pair;
import cz.hartrik.common.Point;
import cz.hartrik.common.Streams;
import cz.hartrik.sg2.tool.Shape;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Poskytuje nástroje, které umožňují další manipulaci s elementy uvnitř tvaru.
 * Všechny iterátory vrací pouze body, které jsou uvnitř plátna.
 * 
 * @version 2015-03-28
 * @author Patrik Harag
 */
public class Clip implements Iterable<Element>, Region {
    
    private final ElementArea area;
    private final Shape shape;
    private final int x, y;

    public Clip(ElementArea area, Shape shape) {
        this(area, shape, 0, 0);
    }
    
    public Clip(ElementArea area, Shape shape, int x, int y) {
        this.area = Checker.requireNonNull(area);
        this.shape = Checker.requireNonNull(shape);
        this.x = x;
        this.y = y;
    }
    
    // iterator
    
    @Override
    public Iterator<Element> iterator() {
        return Iterators.map(iteratorPoint(), area::get);
    }
    
    // pouze validní souřadnice
    @Override
    public Iterator<Point> iteratorPoint() {
        return new Iterator<Point>() {

            // obsahuje i souřadnice mimo plátno
            final Iterator<Point> iterator = shape.iterator(x, y);
            Point next = null;
            
            @Override
            public boolean hasNext() {
                if (next != null) return true;
                
                // filtruje souřadnice mimo plátno
                while (iterator.hasNext()) {
                    final Point point = iterator.next();
                    if (area.valid(point)) {
                        this.next = point;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Point next() {
                if (hasNext()) {
                    final Point out = next;
                    this.next = null;
                    return out;
                } else
                    throw new NoSuchElementException();
            }
        };
    }

    @Override
    public Iterator<Pair<Element, Point>> iteratorLabeled() {
        return Iterators.map(iteratorPoint(),
                (point) -> Pair.of(area.get(point), point));
    }
    
    // stream
    
    @Override
    public Stream<Element> stream() {
        return Streams.stream(iterator());
    }
    
    @Override
    public Stream<Point> streamPoint() {
        return Streams.stream(iteratorPoint());
    }
    
    @Override
    public Stream<Pair<Element, Point>> streamLabeled() {
        return Streams.stream(iteratorLabeled());
    }
    
    // for each
    
    @Override
    public void forEach(Consumer<? super Element> consumer) {
        iterator().forEachRemaining(consumer);
    }
    
    @Override
    public void forEach(PointElementConsumer<? super Element> consumer) {
        iteratorLabeled().forEachRemaining(consumer);
    }
    
    @Override
    public void forEachPoint(PointConsumer consumer) {
         iteratorPoint().forEachRemaining(consumer);
    }

    @Override
    public RegionTools getTools() {
        return new RegionToolsImpl(this, area);
    }
    
}