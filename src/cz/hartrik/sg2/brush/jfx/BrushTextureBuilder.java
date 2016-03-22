
package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Color;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.factory.FactoryFunction;
import cz.hartrik.sg2.world.factory.FuncMapFactory;
import cz.hartrik.sg2.world.factory.ISingleInputFactory;
import java.util.function.Function;
import javafx.scene.image.Image;

/**
 * Slouží k vytvoření {@link JFXBrushTexture}.
 *
 * @version 2015-11-19
 * @author Patrik Harag
 */
public class BrushTextureBuilder extends BrushBuilder {

    protected Function<Element, Boolean> producesFunc;
    protected ISingleInputFactory<Color, Element> factory;
    protected Image texture;

    public BrushTextureBuilder(BrushCollectionBuilder collectionBuilder) {
        super(collectionBuilder);
    }

    // settery

    @Override
    public BrushTextureBuilder setBrushInfo(BrushInfo brushInfo) {
        super.setBrushInfo(brushInfo);
        return this;
    }

    @Override
    public BrushTextureBuilder hidden() {
        super.hidden();
        return this;
    }

    public BrushTextureBuilder setTexture(Image image) {
        this.texture = image;
        return this;
    }

    public BrushTextureBuilder setTexture(String url) {
        this.texture = new Image(url);
        return this;
    }

    public BrushTextureBuilder setProducer(Function<Element, Boolean> func) {
        this.producesFunc = func;
        return this;
    }

    public BrushTextureBuilder setSFactory(
            ISingleInputFactory<Color, Element> factory) {

        this.factory = factory;
        return this;
    }

    public BrushTextureBuilder setMFactory(
            FactoryFunction<Color, Element> function) {

        this.factory = new FuncMapFactory<>(function);
        return this;
    }

    // build

    @Override
    protected Brush createBrush() {
        Checker.requireNonNull(factory, "factory cannot be null");
        Checker.requireNonNull(texture, "texture cannot be null");

        if (producesFunc != null) {
            return new JFXBrushTexture<Element>(brushInfo, texture, factory) {
                @Override public boolean produces(Element element) {
                    return producesFunc.apply(element);
                }
            };
        }

        return new JFXBrushTexture<>(brushInfo, texture, factory);
    }

}