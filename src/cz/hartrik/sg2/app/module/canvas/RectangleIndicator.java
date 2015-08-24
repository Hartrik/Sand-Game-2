
package cz.hartrik.sg2.app.module.canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Obdélníkový výběr.
 * 
 * @version 2015-03-20
 * @author Patrik Harag
 */
public class RectangleIndicator extends ANodeCursor<Rectangle> {

    private final double initialX, initialY;

    public RectangleIndicator(CanvasWithCursor canvas, double initX, double initY) {
        super(canvas, createCursor(initX, initY));
        
        this.initialX = initX + canvas.xLocation;
        this.initialY = initY + canvas.yLocation;
    }
    
    private static Rectangle createCursor(double x, double y) {
        Rectangle rectangle = new Rectangle(x, y, 0, 0);
        rectangle.setFill(new Color(0, 0, 0, 0.3));
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(0.5);
        return rectangle;
    }
    
    // Cursor
    
    @Override
    public void addCursor() {
        cursor.setVisible(false);
        canvas.zoomGroup.getChildren().add(cursor);
    }

    @Override
    public void removeCursor() {
        canvas.zoomGroup.getChildren().remove(cursor);
    }
    
    @Override
    public void onMove(double mX, double mY) {
        mX = (mX < 0 ? 0 : mX) + canvas.xLocation;
        mY = (mY < 0 ? 0 : mY) + canvas.yLocation;
        
        setSizes(
                (mX > initialX ? initialX : mX),
                (mY > initialY ? initialY : mY),
                (mX > initialX ? mX - initialX : initialX - mX),
                (mY > initialY ? mY - initialY : initialY - mY)
        );
        
        if (!cursor.isVisible())
            cursor.setVisible(true);
    }

    protected void setSizes(double x, double y, double w, double h) {
        x = (x < canvas.xLocation ? canvas.xLocation : x);
        y = (y < canvas.yLocation ? canvas.yLocation : y);
        
        w = (x + w > canvas.xLocation + canvas.imageView.getFitWidth()
                ? canvas.imageView.getFitWidth() - x + canvas.xLocation
                : w);
        h = (y + h > canvas.yLocation + canvas.imageView.getFitHeight()
                ? canvas.imageView.getFitHeight() - y + canvas.yLocation
                : h);
        
        cursor.setX(x);
        cursor.setY(y);
        cursor.setWidth(w);
        cursor.setHeight(h);
    }
    
}