
package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.brush.manage.BrushInfoLoader;
import cz.hartrik.sg2.brush.manage.BrushItem;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.factory.FactoryFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javafx.scene.image.Image;

/**
 * Slouží k tvorbě kolekce štětců.
 *
 * @version 2016-07-02
 * @author Patrik Harag
 */
public class BrushCollectionBuilder {

    protected final BrushInfoLoader loader;
    protected final List<BrushItem> collection;

    public BrushCollectionBuilder(BrushInfoLoader loader) {
        this.loader = loader;
        this.collection = new ArrayList<>();
    }

    public List<BrushItem> getCollection() {
        return collection;
    }

    public BrushInfo load(int id) {
        return loader.get(id);
    }

    // add methods

    // - random

    public BrushRandomBuilder addRnd(int id) {
        return addRnd(loader.get(id));
    }

    public BrushRandomBuilder addRnd(BrushInfo brushInfo) {
        return new BrushRandomBuilder(this)
                .setBrushInfo(brushInfo);
    }

    public BrushCollectionBuilder addRnd(int id, Element... elements) {
        new BrushRandomBuilder(this)
                .setBrushInfo(loader.get(id))
                .setElements(elements)
                .build();

        return this;
    }

    public BrushCollectionBuilder addRnd(int id, String url, Element... elements) {
        new BrushRandomBuilder(this)
                .setBrushInfo(loader.get(id))
                .setElements(elements)
                .setImage(url)
                .build();

        return this;
    }

    // - textured

    public BrushTextureBuilder addTex(int id) {
        return addTex(loader.get(id));
    }

    public BrushTextureBuilder addTex(BrushInfo brushInfo) {
        return new BrushTextureBuilder(this)
                .setBrushInfo(brushInfo);
    }

    public BrushCollectionBuilder addTex(int id, String url,
            FactoryFunction<Color, Element> function) {

        new BrushTextureBuilder(this)
                .setBrushInfo(loader.get(id))
                .setTexture(url)
                .setMFactory(function)
                .build();

        return this;
    }

    // - simple

    public BrushSimpleBuilder addSim(int id) {
        return addSim(loader.get(id));
    }

    public BrushSimpleBuilder addSim(BrushInfo brushInfo) {
        return new BrushSimpleBuilder(this)
                .setBrushInfo(brushInfo);
    }

    // - unknown

    public BrushCollectionBuilder add(Brush brush, String url) {
        return add(brush, new Image(url));
    }

    public BrushCollectionBuilder add(Brush brush, Image img) {
        return add(Thumbnails.addThumb(img, brush));
    }

    public BrushCollectionBuilder add(Brush brush) {
        return add(new BrushItem(brush, false));
    }

    public BrushCollectionBuilder addHidden(Brush brush) {
        return add(new BrushItem(brush, true));
    }

    public BrushCollectionBuilder add(BrushItem brush) {
        collection.add(brush);

        return this;
    }

    public BrushCollectionBuilder add(int id, Function<BrushInfo, Brush> func) {
        return add(func.apply(loader.get(id)));
    }

    public BrushCollectionBuilder add(int id, Image img, Function<BrushInfo, Brush> func) {
        return add(func.apply(loader.get(id)), img);
    }

    public BrushCollectionBuilder add(int id, String img, Function<BrushInfo, Brush> func) {
        return add(id, new Image(img), func);
    }

    public BrushCollectionBuilder addHidden(int id, Function<BrushInfo, Brush> func) {
        return addHidden(func.apply(loader.get(id)));
    }

    public BrushCollectionBuilder addHidden(int id, Image img, Function<BrushInfo, Brush> func) {
        return addHidden(Thumbnails.addThumb(img, func.apply(loader.get(id))));
    }

    public BrushCollectionBuilder addHidden(int id, String img, Function<BrushInfo, Brush> func) {
        return addHidden(id, new Image(img), func);
    }

}