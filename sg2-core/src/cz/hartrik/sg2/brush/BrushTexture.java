
package cz.hartrik.sg2.brush;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.engine.Image;
import cz.hartrik.sg2.random.XORShiftRandom;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import java.util.function.Function;

/**
 * Štětec, který umožňuje nanášet elementy obarvené podle nějaké textury.
 *
 * @version 2017-08-06
 * @author Patrik Harag
 * @param <E> element
 */
public class BrushTexture<E extends Element> extends ABrushBase {

    protected final Function<Color, E> factory;
    protected final Image image;
    protected final XORShiftRandom random = new XORShiftRandom();

    public BrushTexture(BrushInfo brushInfo, Image image,
                        Function<Color, E> factory) {

        super(brushInfo);
        this.image = image;
        this.factory = factory;
    }

    @Override
    public E getElement(Element current, int x, int y, ElementArea area,
            Controls controls) {

        if (x == -1 || y == -1)
            return getElement(current);  // náhodná barva

        final int imgX = x % image.getWidth();
        final int imgY = y % image.getHeight();
        return factory.apply(image.getColor(imgX, imgY));
    }

    @Override
    public E getElement(Element current) {
        return factory.apply(randomColor());
    }

    private Color randomColor() {
        int x = random.nextInt(image.getWidth());
        int y = random.nextInt(image.getHeight());
        return image.getColor(x, y);
    }

    @Override
    public boolean isAdvanced() {
        return true;
    }

}