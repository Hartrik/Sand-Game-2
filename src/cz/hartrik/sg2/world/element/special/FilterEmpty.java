package cz.hartrik.sg2.world.element.special;

import cz.hartrik.sg2.world.element.type.Filterable;
import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;

/**
 * Element představující prázdný filtr.
 * 
 * @version 2015-02-15
 * @author Patrik Harag
 */
public class FilterEmpty extends Filter {

    private static final long serialVersionUID = 83715083867368_02_085L;
    
    public FilterEmpty(Color color) {
        super(color);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (y == 0) return;  // nic nemůže být nad
        
        final int above = y - 1;
        final Element element = world.get(x, above);
        
        if (element instanceof Filterable) {
            final Filterable filterable = (Filterable) element;
            
            if (filterable.filter()) {
                Element onFilter = filterable.onFilter(x, above, tools, world);
                
                world.setAndChange(x, above, onFilter);
                world.setAndChange(x, y, new FilterFull<>(this, filterable));
            }
        }
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return (y == 0) ? false : world.get(x, y - 1) instanceof Filterable;
    }
    
}