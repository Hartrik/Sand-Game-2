package cz.hartrik.sg2.tool;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Point;
import java.util.Iterator;

/**
 * Třída představující obdélník. Pozicí se určuje levý horní roh.
 * 
 * @version 2015-03-19
 * @author Patrik Harag
 */
public class Rectangle extends AbstractShape {

    protected final int width;
    protected final int height;

    public Rectangle(int width, int height) {
        this.width  = Checker.checkArgument(width,  width  > 0);
        this.height = Checker.checkArgument(height, height > 0);
    }

    @Override
    public Iterator<Point> iterator(int x, int y) {
        return new RectangleIterator(x, y, width, height);
    }

    @Override
    public Iterator<Point> iterator(int x, int y, int width, int height) {
        return new RectangleIterator(x, y, width, height);
    }
    
    // gettery

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}