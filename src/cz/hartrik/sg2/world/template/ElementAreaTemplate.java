package cz.hartrik.sg2.world.template;

import cz.hartrik.sg2.engine.render.JFXRenderer;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.Inserter;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class ElementAreaTemplate implements TemplateWPreview {

    private final ElementArea elementArea;

    public ElementAreaTemplate(ElementArea elementArea) {
        this.elementArea = elementArea;
    }

    @Override
    public void insert(Inserter<? extends ElementArea> inserter, int x, int y) {
        elementArea.forEachPoint((int ix, int iy) -> {
            if (inserter.insert((x + ix), (y + iy), elementArea.get(ix, iy))) {
                float temp = elementArea.getTemperature(ix, iy);
                inserter.getArea().setTemperature((x + ix), (y + iy), temp);
            }
        });
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
        renderer.updateBuffer();

        image.getPixelWriter().setPixels(
                0, 0, getWidth(), getHeight(),
                renderer.getPixelFormat(),
                renderer.getBuffer(), 0, (getWidth() * 4));

        return image;
    }

}