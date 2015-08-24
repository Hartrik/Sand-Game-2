package cz.hartrik.sg2.app.module.canvas;

import javafx.scene.Node;

/**
 * Abstraktní třída pro obdélníkové kurzory.
 * 
 * @version 2015-03-20
 * @author Patrik Harag
 */
abstract class ARectangularCursor<T extends Node> extends ANodeCursor<T> {

    protected final int width;
    protected final int height;

    public ARectangularCursor(CanvasWithCursor canvas, T cursor, int w, int h) {
        super(canvas, cursor);
        
        this.width = w;
        this.height = h;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}