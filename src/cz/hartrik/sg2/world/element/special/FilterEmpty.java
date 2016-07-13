package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;

/**
 * Element představující prázdný filtr.
 *
 * @version 2016-06-15
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

            if (filterable.testFilter()) {
                Filterable filtered = filterable.filter();

                world.setAndChange(x, above, world.getBackground());
                world.setAndChange(x, y, new FilterFull<>(this, filtered));

                tools.swapTemperature(x, y, x, above);
            }
        }
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return (y == 0) ? false : world.get(x, y - 1) instanceof Filterable;
    }

}