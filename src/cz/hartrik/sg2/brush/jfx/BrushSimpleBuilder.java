
package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Color;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.BrushSimple;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.factory.ISingleInputFactory;
import java.util.function.Function;

/**
 * Slouží k vytvoření {@link BrushSimple}.
 *
 * @version 2015-11-19
 * @author Patrik Harag
 */
public class BrushSimpleBuilder extends BrushBuilder {

    protected Function<Element, Boolean> isProducerFunc;
    protected ISingleInputFactory<Color, ? extends Element> factory;
    protected Color[] colors;

    public BrushSimpleBuilder(BrushCollectionBuilder collectionBuilder) {
        super(collectionBuilder);
    }

    @Override
    public BrushSimpleBuilder setBrushInfo(BrushInfo brushInfo) {
        super.setBrushInfo(brushInfo);
        return this;
    }

    @Override
    public BrushSimpleBuilder hidden() {
        super.hidden();
        return this;
    }

    public BrushSimpleBuilder setProducer(Function<Element, Boolean> func) {
        this.isProducerFunc = func;
        return this;
    }

    public BrushSimpleBuilder setFactory(
            ISingleInputFactory<Color, ? extends Element> factory) {

        this.factory = factory;
        return this;
    }

    public BrushSimpleBuilder setColors(Color... colors) {
        this.colors = colors;
        return this;
    }

    @Override
    protected Brush createBrush() {
        Checker.requireNonNull(factory, "factory cannot be null");

        if (colors == null)
            colors = new Color[0];

        if (isProducerFunc != null) {
            return new BrushSimple(brushInfo, factory, colors) {
                @Override public boolean isProducer(Element element) {
                    return isProducerFunc.apply(element);
                }
            };
        }

        return new BrushSimple(brushInfo, factory, colors);
    }

}