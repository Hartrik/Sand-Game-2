package cz.hartrik.sg2.app.sandbox.element;

import cz.hartrik.sg2.brush.BrushRandom;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.brush.build.Thumbnailable;
import cz.hartrik.sg2.brush.build.Thumbnails;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.engine.Image;
import cz.hartrik.sg2.random.XORShiftRandom;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import java.util.function.IntUnaryOperator;

/**
 * Speciální štětec pro nanášení trávy.
 *
 * @version 2015-11-19
 * @author Patrik Harag
 */
public class GrassBrush extends BrushRandom implements Thumbnailable {

    private final IntUnaryOperator operator;

    public GrassBrush(BrushInfo info, IntUnaryOperator operator,
                      Element... elements) {

        super(info, elements);
        this.operator = operator;
    }

    @Override
    public Element getElement(Element current) {
        return random.randomElement(elements);
    }

    @Override
    public boolean isAdvanced() {
        return true;
    }

    @Override
    public Element getElement(Element current, int x, int y,
            ElementArea area, Controls controls) {

        return chooseRandom(operator.applyAsInt(x), elements);
    }

    public static <T> T chooseRandom(int x, T[] array) {
        if (x < 0) return null;
        return array[XORShiftRandom.nextRandom(x, array.length)];
    }

    @Override
    public Image getThumb(int width, int height) {
        return Thumbnails.generateThumb(this, width, height);
    }

}