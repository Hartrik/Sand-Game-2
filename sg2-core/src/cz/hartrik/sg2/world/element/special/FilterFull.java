package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.Container;

/**
 * Element představující filtr, kterým právě prochází nějaký element.
 * Filtr zachovává tepelné vlastnosti elementů, které do něj vstoupí - tj.
 * teplo se šíří i uvnitř filtru.
 *
 * @version 2016-06-15
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
        if (!moveInFilter(x, y, x, y + 1, world, tools))
             moveInFilter(x, y, x + tools.randomSide(), y, world, tools);
    }

    private boolean moveInFilter(
            int x, int y, int nx, int ny, World world, Tools tools) {

        if (!world.valid(nx, ny)) return false;
        final Element nextElement = world.get(nx, ny);

        if (nextElement instanceof FilterEmpty) {
            // pokračuje dalším filtrem

            if (element.testFilter()) {
                Element nextFilter = new FilterFull<>(nextElement, element);
                tools.swap(x, y, nextFilter, nx, ny, empty);
            }
            return true;

        } else if (nextElement instanceof Air) {
            // element prošel filtrem

            tools.swap(x, y, element, nx, ny, empty);
            return true;
        }
        return false;
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        if (y == 0) return false;

        return tools.getDirVisitor().test(x, y, (next) -> {
            return next instanceof Element || next instanceof Air;
        }, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
    }

    @Override
    public boolean hasTemperature() {
        return element.hasTemperature();
    }

    @Override
    public boolean isConductive() {
        return element.isConductive();
    }

    @Override
    public float getConductiveIndex() {
        return element.getConductiveIndex();
    }

    @Override
    public float loss() {
        return element.loss();
    }

    @Override
    public Color getColor() {
        return getColor(World.DEFAULT_TEMPERATURE);
    }

    @Override
    public Color getColor(float temp) {
        return element.getColor(temp).blend(super.getColor().changeAlpha(0.8f));
    }

    // Container

    @Override
    public T getElement() {
        return element;
    }

}