
package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import java.util.function.Function;
import javafx.scene.image.Image;

/**
 * Slouží k vytvoření {@link JFXBrushRandom}.
 * 
 * @version 2014-05-22
 * @author Patrik Harag
 */
public class BrushRandomBuilder extends BrushBuilder {
    
    protected Element[] elements;
    protected Function<Element, Boolean> isProducerFunc;
    protected Image image;
    
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
    
    public BrushRandomBuilder setElements(Element... elements) {
        this.elements = elements;
        return this;
    }
    
    public BrushRandomBuilder setImage(Image image) {
        this.image = image;
        return this;
    }
    
    public BrushRandomBuilder setImage(String url) {
        this.image = new Image(url);
        return this;
    }

    public BrushRandomBuilder setProducer(
            Function<Element, Boolean> isProducerFunc) {
        
        this.isProducerFunc = isProducerFunc;
        return this;
    }
    
    // build
    
    @Override
    public BrushCollectionBuilder build() {
        if (brushInfo == null) return collectionBuilder;
        
        Brush brush;
        if (isProducerFunc != null) {
            brush = new JFXBrushRandom(brushInfo, elements) {
                @Override public boolean isProducer(Element element) {
                    return isProducerFunc.apply(element);
                }
            };
        } else {
            brush = new JFXBrushRandom(brushInfo, elements);
        }
        
        if (image != null)
            brush = Thumbnails.addThumb(image, brush);
        
        return collectionBuilder.add(brush, hidden);
    }
    
}