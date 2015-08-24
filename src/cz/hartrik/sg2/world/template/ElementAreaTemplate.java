package cz.hartrik.sg2.world.template;

import cz.hartrik.sg2.engine.JFXRenderer;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.Inserter;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * @version 2015-01-11
 * @author Patrik Harag
 */
public class ElementAreaTemplate implements TemplateWPreview {
    
    private final ElementArea elementArea;

    public ElementAreaTemplate(ElementArea elementArea) {
        this.elementArea = elementArea;
    }

    @Override
    public void insert(Inserter<? extends ElementArea> inserter, int x, int y) {
        for (int iy = 0; iy < getHeight(); iy++)
            for (int ix = 0; ix < getWidth(); ix++)
                inserter.insert((x + ix), (y + iy), elementArea.get(ix, iy));
    }
    
    @Override
    public boolean isResponsive() {
        return false;
    }

    @Override
    public int getWidth() {
        return elementArea.getWidth();
    }

    @Override
    public int getHeight() {
        return elementArea.getHeight();
    }
    
    @Override
    public Image getImage() {
        WritableImage image = new WritableImage(getWidth(), getHeight());
        
        JFXRenderer renderer = new JFXRenderer(elementArea);
        renderer.updateBufferAll();
        
        image.getPixelWriter().setPixels(
                0, 0, getWidth(), getHeight(),
                renderer.getPixelFormat(),
                renderer.getData(), 0, (getWidth() * 4));
        
        return image;
    }
    
}