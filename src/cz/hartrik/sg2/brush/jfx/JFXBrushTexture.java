
package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.XORShiftRandom;
import cz.hartrik.sg2.brush.ABrushBase;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import java.util.function.Function;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

/**
 * Štětec, který umožňuje nanášet elementy obarvené podle nějaké textury.
 *
 * @version 2015-11-19
 * @author Patrik Harag
 * @param <E> element
 */
public class JFXBrushTexture<E extends Element> extends ABrushBase {

    protected final Function<Color, E> factory;
    protected final Image image;
    protected final PixelReader reader;
    protected final XORShiftRandom random = new XORShiftRandom();

    public JFXBrushTexture(BrushInfo brushInfo, Image image,
            Function<Color, E> factory) {

        super(brushInfo);
        this.image = image;
        this.factory = factory;
        this.reader = image.getPixelReader();
    }

    @Override
    public E getElement(Element current, int x, int y, ElementArea area,
            Controls controls) {

        if (x == -1 || y == -1)
            return getElement(current);  // náhodná barva

        final int imgX = x % (int) image.getWidth();
        final int imgY = y % (int) image.getHeight();
        final int argb = reader.getArgb(imgX, imgY);

        return factory.apply(Color.createARGB(argb));
    }

    protected Color randomColor() {
        int x = random.nextInt((int) image.getWidth());
        int y = random.nextInt((int) image.getHeight());
        return Color.createARGB(reader.getArgb(x, y));
    }

    @Override
    public E getElement(Element current) {
        return factory.apply(randomColor());
    }

    @Override
    public boolean isAdvanced() {
        return true;
    }

}