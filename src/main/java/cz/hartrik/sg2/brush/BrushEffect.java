
package cz.hartrik.sg2.brush;

import cz.hartrik.common.Checker;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.factory.IAdvancedBrushFactory;
import cz.hartrik.sg2.world.factory.ISingleInputFactory;
import java.util.function.Predicate;

/**
 * Štětec, který se hodí k ovlivňování vlastností stávajících elementů.
 *
 * @version 2016-07-02
 * @author Patrik Harag
 */
public class BrushEffect extends ABrushBase implements EffectBrush {

    private final ISingleInputFactory<Element, ? extends Element> factory;
    private final IAdvancedBrushFactory<? extends Element> advancedFactory;

    private Predicate<Element> producesPredicate = (e) -> false;

    public BrushEffect(BrushInfo brushInfo,
            ISingleInputFactory<Element, ? extends Element> factory) {

        super(brushInfo);
        this.factory = Checker.requireNonNull(factory);
        this.advancedFactory = null;
    }

    public BrushEffect(BrushInfo brushInfo,
            IAdvancedBrushFactory<? extends Element> advancedFactory) {

        super(brushInfo);
        this.factory = null;
        this.advancedFactory = Checker.requireNonNull(advancedFactory);
    }

    public void setPredicate(Predicate<Element> producesPredicate) {
        this.producesPredicate = producesPredicate;
    }

    @Override
    public Element getElement(Element current) {
        if (isAdvanced())
            return advancedFactory.getElement(current, -1, -1, null, null);
        else
            return factory.apply(current);
    }

    @Override
    public Element getElement(Element current, int x, int y, ElementArea area,
            Controls controls) {

        if (isAdvanced())
            return advancedFactory.getElement(current, x, y, area, controls);
        else
            return factory.apply(current);
    }

    @Override
    public boolean isAdvanced() {
        return advancedFactory != null;
    }

    @Override
    public boolean produces(Element element) {
        return producesPredicate.test(element);
    }

}