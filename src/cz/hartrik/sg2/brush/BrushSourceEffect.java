
package cz.hartrik.sg2.brush;

import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.element.special.Source;

/**
 * Vytváří zdroje podle elementů, na které je nanesen.
 *
 * @version 2016-07-02
 * @author Patrik Harag
 */
public class BrushSourceEffect extends BrushSource implements EffectBrush {

    private final BrushManager brushManager;

    public BrushSourceEffect(BrushInfo brushInfo, int ratioChance,
            BrushManager brushManager) {

        super(brushInfo, ratioChance);
        this.brushManager = brushManager;
    }

    @Override
    public Element getElement(Element current, int x, int y, ElementArea area,
            Controls controls) {

        return getSource(current);
    }

    protected Source getSource(Element element) {
        if (element instanceof Source)
            return null;

        Brush producer = brushManager.getProducer(element);
        return (producer != null) ? getSource(producer) : null;
    }

}