
package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.manage.BrushItem;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.brush.manage.BrushInfoLoader;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.factory.FactoryFunction;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

/**
 * Slouží k tvorbě kolekce štětců.
 * 
 * @version 2014-05-22
 * @author Patrik Harag
 */
public class BrushCollectionBuilder {
    
    protected final BrushInfoLoader loader;
    protected final List<BrushItem<Brush>> collection;

    public BrushCollectionBuilder(BrushInfoLoader loader) {
        this.loader = loader;
        this.collection = new ArrayList<>();
    }
    
    public List<BrushItem<Brush>> getCollection() {
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
    
    // - source
    
    public BrushCollectionBuilder addSrc(int id, String url, int chance) {
        final Image texture = new Image(url);
        final BrushInfo brushInfo = loader.get(id);
        return add(new JFXBrushSource(brushInfo, texture, chance));
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
        return add(brush, false);
    }
    
    public BrushCollectionBuilder add(Brush brush, boolean hidden) {
        return add(new BrushItem<>(brush, hidden));
    }
    
    public BrushCollectionBuilder add(BrushItem<Brush> brush) {
        collection.add(brush);
        
        return this;
    }
    
}