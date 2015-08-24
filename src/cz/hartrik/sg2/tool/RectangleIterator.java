package cz.hartrik.sg2.tool;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Point;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterátor, který bude postupně procházet body nacházející se v obdélníku.
 * Body prochází od levého horního doprava.
 * 
 * @version 2015-03-15
 * @author Patrik Harag
 */
public class RectangleIterator implements Iterator<Point> {
        
    private final int startX;
    private final int startY;
    private final int width;
    private final int height;

    private int i = 0;

    public RectangleIterator(int width, int height) {
        this(0, 0, width, height);
    }
    
    public RectangleIterator(int x, int y, int width, int height) {
        this.startX = x;
        this.startY = y;
        this.width = Checker.checkArgument(width, width > 0);
        this.height = Checker.checkArgument(height, height > 0);
    }

    @Override
    public boolean hasNext() {
        return i < width * height;
    }

    @Override
    public Point next() {
        if (i > width * height)
            throw new NoSuchElementException();
        else
            return Point.of(startX + (i % width), startY + (i++ / width));
    }

}