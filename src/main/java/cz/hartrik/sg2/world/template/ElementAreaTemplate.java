package cz.hartrik.sg2.world.template;

import cz.hartrik.sg2.engine.Image;
import cz.hartrik.sg2.engine.Platform;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.Inserter;

/**
 * @version 2017-08-06
 * @author Patrik Harag
 */
public class ElementAreaTemplate implements Template {

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
        return Platform.get().renderPreview(elementArea);
    }

}