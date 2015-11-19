
package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.BrushRandom;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import java.util.function.Function;
import javafx.scene.image.Image;

/**
 * Slouží k vytvoření {@link BrushRandom}.
 *
 * @version 2015-11-19
 * @author Patrik Harag
 */
public class BrushRandomBuilder extends BrushBuilder {

    protected Element[] elements;
    protected Function<Element, Boolean> isProducerFunc;

    public BrushRandomBuilder(BrushCollectionBuilder collectionBuilder) {
        super(collectionBuilder);
    }

    // settery

    @Override
    public BrushRandomBuilder setBrushInfo(BrushInfo brushInfo) {
        super.setBrushInfo(brushInfo);
        return this;
    }

    @Override
    public BrushRandomBuilder hidden() {
        super.hidden();
        return this;
    }

    @Override
    public BrushRandomBuilder setImage(Image image) {
        super.setImage(image);
        return this;
    }

    @Override
    public BrushRandomBuilder setImage(String url) {
        super.setImage(url);
        return this;
    }

    public BrushRandomBuilder setElements(Element... elements) {
        this.elements = elements;
        return this;
    }

    public BrushRandomBuilder setProducer(
            Function<Element, Boolean> isProducerFunc) {

        this.isProducerFunc = isProducerFunc;
        return this;
    }

    // build

    @Override
    protected Brush createBrush() {
        if (isProducerFunc != null) {
            return new BrushRandom(brushInfo, elements) {
                @Override public boolean isProducer(Element element) {
                    return isProducerFunc.apply(element);
                }
            };
        }

        return new BrushRandom(brushInfo, elements);
    }

}