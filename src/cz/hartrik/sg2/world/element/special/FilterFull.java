package cz.hartrik.sg2.world.element.special;

import cz.hartrik.sg2.world.element.type.Filterable;
import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.type.Container;

/**
 * Element představující filtr, kterým právě prochází nějaký element.
 * 
 * @version 2015-02-15
 * @author Patrik Harag
 * @param <T> typ elementu, který je uvnitř filtru
 */
public class FilterFull<T extends Filterable> extends Filter
        implements Container<T> {
    
    private static final long serialVersionUID = 83715083867368_02_086L;
    
    private final Element empty;
    private final T element;

    public FilterFull(Element emptyFilter, T element) {
        super(emptyFilter.getColor());
        this.empty = emptyFilter;
        this.element = element;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (!move(x, y, world, x, y + 1))
             move(x, y, world, x + tools.randomSide(), y);
    }

    private boolean move(int x, int y, World world, int nx, int ny) {
        if (!world.valid(nx, ny)) return false;
        final Element nextElement = world.get(nx, ny);
        
        if (nextElement instanceof FilterEmpty) {
            // pokračuje dalším filtrem
            
            if (element.filter()) {
                world.setAndChange( x,  y, empty);
                world.setAndChange(nx, ny, new FilterFull<>(nextElement, element));
            }
            return true;
        
        } else if (nextElement instanceof Air) {
            // element prošel filtrem
            
            world.setAndChange( x,  y, empty);
            world.setAndChange(nx, ny, element);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        if (y == 0) return false;
        
        return tools.getDirectionVisitor().test(x, y, (next) -> {
            return next instanceof Element || next instanceof Air;
        }, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
    }
    
    @Override
    public T getElement() {
        return element;
    }

    @Override
    public Color getColor() {
        return element.getColor().blend(super.getColor().changeAlpha(0.8f));
    }

}