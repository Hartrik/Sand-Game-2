package cz.hartrik.sg2.app.module.canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Obdélníkový kurzor.
 *
 * @version 2015-03-21
 * @author Patrik Harag
 */
public class RectangleCursor extends ARectangularCursor<Rectangle> {

    public RectangleCursor(CanvasWithCursor canvas, int width, int height) {
        this(canvas, createCursor(width, height));
    }

    public RectangleCursor(CanvasWithCursor canvas, Rectangle rect) {
        super(canvas, rect, (int) rect.getWidth(), (int) rect.getHeight());
    }

    private static Rectangle createCursor(int width, int height) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(new Color(0, 0, 0, 0.3));
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(0.5);
        return rectangle;
    }

    // Cursor

    @Override
    public void onMove(double mX, double mY) {
        double x = mX + canvas.xLocation;
        double y = mY + canvas.yLocation;

        cursor.setWidth(width);
        cursor.setHeight(height);

        // levá
        if (x < canvas.xLocation) {
            cursor.setWidth(Math.floor(width - canvas.xLocation + x));
            x = canvas.xLocation;
        }

        // horní
        if (y < canvas.yLocation) {
            cursor.setHeight(Math.floor(height - canvas.yLocation + y));
            y = canvas.yLocation;
        }

        // pravá, dolní
        double rBound = canvas.xLocation + canvas.fxcanvas.getWidth();
        double dBound = canvas.yLocation + canvas.fxcanvas.getHeight();

        if (x + width  > rBound) cursor.setWidth(Math.ceil(rBound - x));
        if (y + height > dBound) cursor.setHeight(Math.ceil(dBound - y));

        cursor.setX(Math.floor(x));
        cursor.setY(Math.floor(y));
        cursor.setVisible(true);
    }

}