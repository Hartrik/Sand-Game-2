package cz.hartrik.sg2.brush;

import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;

/**
 * Abstraktní třída pro snazší implementaci štětců, které vznikají skládáním.
 *
 * @version 2015-11-19
 * @author Patrik Harag
 */
public class ABrushWrapper implements Brush, Wrapper {

    private final Brush brush;

    public ABrushWrapper(Brush brush) {
        this.brush = brush;
    }

    @Override
    public Element getElement(Element current) {
        return brush.getElement(current);
    }

    @Override
    public Element getElement(Element current, int x, int y,
            ElementArea area, Controls controls) {

        return brush.getElement(current, x, y, area, controls);
    }

    @Override
    public BrushInfo getInfo() {
        return brush.getInfo();
    }

    @Override
    public boolean isAdvanced() {
        return brush.isAdvanced();
    }

    @Override
    public boolean produces(Element element) {
        return brush.produces(element);
    }

    @Override
    public Brush getOriginal() {
        return brush;
    }

}