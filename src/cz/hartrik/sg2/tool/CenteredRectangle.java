
package cz.hartrik.sg2.tool;

import cz.hartrik.common.Point;
import java.util.Iterator;

/**
 * Třída představující obdélník, který se umísťuje podle středu - na rozdíl od
 * {@link Rectangle}, který se umísťuje podle levého horního rohu.
 * Pokud bude výška nebo šířka sudé číslo, přebývat bude na levé nebo dolní
 * straně.
 *
 * @version 2015-03-19
 * @author Patrik Harag
 */
public class CenteredRectangle extends Rectangle {

    public CenteredRectangle(int width, int height) {
        super(width, height);
    }

    @Override
    public Iterator<Point> iterator(int x, int y) {
        final int cX = (int) Math.ceil(x - getWidth() / 2.);
        final int cY = (int) Math.ceil(y - getHeight() / 2.);
        return new RectangleIterator(cX, cY, getWidth(), getHeight());
    }

}