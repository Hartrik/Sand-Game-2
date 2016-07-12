
package cz.hartrik.sg2.app.extension.canvas;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Umožňuje přiblížení {@link ImageView} uvnitř {@link ScrollPane} a použití
 * vlastního kurzoru.
 *
 * @version 2014-12-27
 * @author Patrik Harag
 */
public class CanvasWithCursor extends SGCanvas {

    protected Cursor cursor = null;
    protected EventHandler<? super MouseEvent> onMove;
    protected EventHandler<? super MouseEvent> onOver;

    private double lastX = -1d, lastY = -1d;

    public CanvasWithCursor(ScrollPane scrollPane, Canvas canvas) {
        super(scrollPane, canvas);
    }

    public void setCursor(Cursor cursor) {
        removeCursor();

        this.cursor = cursor;

        cursor.addCursor();
        cursor.addListeners();

        onMove = event -> {
            lastX = event.getX();
            lastY = event.getY();
            cursor.onMove(lastX, lastY);
        };

        onOver = event -> {
            lastX = lastY = -1;
            cursor.onOver();
        };

        fxcanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMove);
        fxcanvas.addEventHandler(MouseEvent.MOUSE_MOVED, onMove);
        fxcanvas.addEventHandler(MouseEvent.MOUSE_EXITED, onOver);
    }

    public void removeCursor() {
        if (cursor != null) {
            cursor.removeListeners();
            cursor.removeCursor();
            fxcanvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, onMove);
            fxcanvas.removeEventHandler(MouseEvent.MOUSE_MOVED, onMove);
            fxcanvas.removeEventHandler(MouseEvent.MOUSE_EXITED, onOver);
        }
    }

}