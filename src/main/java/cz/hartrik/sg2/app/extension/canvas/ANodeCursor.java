package cz.hartrik.sg2.app.extension.canvas;

import javafx.scene.Node;

/**
 * Abstraktní třída pro kurzory tvořené jedním uzlem.
 *
 * @version 2015-03-29
 * @author Patrik Harag
 * @param <T> typ kurzoru
 */
public abstract class ANodeCursor<T extends Node> implements Cursor {

    protected final T cursor;
    protected final CanvasWithCursor canvas;

    public ANodeCursor(CanvasWithCursor canvas, T cursor) {
        this.canvas = canvas;
        this.cursor = cursor;

        cursor.setMouseTransparent(true);
    }

    // Cursor

    @Override
    public void addCursor() {
        cursor.setVisible(false);
        canvas.fxcanvas.setCursor(javafx.scene.Cursor.NONE);
        canvas.zoomGroup.getChildren().add(cursor);
    }

    @Override
    public void removeCursor() {
        canvas.fxcanvas.setCursor(javafx.scene.Cursor.DEFAULT);
        canvas.zoomGroup.getChildren().remove(cursor);
    }

    @Override public void addListeners() { }
    @Override public void removeListeners() { }

    @Override
    public abstract void onMove(double mX, double mY);

    @Override
    public void onOver() {
        cursor.setVisible(false);
    }

    public T getCursor() {
        return cursor;
    }

}