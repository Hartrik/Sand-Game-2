
package cz.hartrik.sg2.app.module.canvas;

import javafx.event.EventHandler;
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
public class CanvasWithCursor extends Canvas {

    protected Cursor cursor = null;
    protected EventHandler<? super MouseEvent> onMove;
    protected EventHandler<? super MouseEvent> onOver;
    
    private double lastX = -1d, lastY = -1d;
    
    public CanvasWithCursor(ScrollPane scrollPane, ImageView imageView) {
        super(scrollPane, imageView);
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
        
        imageView.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMove);
        imageView.addEventHandler(MouseEvent.MOUSE_MOVED, onMove);
        imageView.addEventHandler(MouseEvent.MOUSE_EXITED, onOver);
        
        // jinak by se nový kurzor nezobrazil, dokud by nedošlo k pohybu myší
//        if (lastX != -1 && lastY != -1)
//            cursor.onMove(lastX, lastY);
    }

    public void removeCursor() {
        if (cursor != null) {
            cursor.removeListeners();
            cursor.removeCursor();
            imageView.removeEventHandler(MouseEvent.MOUSE_DRAGGED, onMove);
            imageView.removeEventHandler(MouseEvent.MOUSE_MOVED, onMove);
            imageView.removeEventHandler(MouseEvent.MOUSE_EXITED, onOver);
        }
    }

}