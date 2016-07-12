package cz.hartrik.sg2.app.extension.canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @version 2015-03-20
 * @author Patrik Harag
 */
public class CircleCursor extends ANodeCursor<Circle> {

    private final int radius;

    public CircleCursor(CanvasWithCursor canvas, int radius) {
        this(canvas, createCursor(radius));
    }

    public CircleCursor(CanvasWithCursor canvas, Circle circle) {
        super(canvas, circle);
        this.radius = (int) circle.getRadius();
    }

    private static Circle createCursor(int radius) {
        Circle rectangle = new Circle(radius);
        rectangle.setFill(new Color(0, 0, 0, 0.3));
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(0.5);
        return rectangle;
    }

    @Override
    public void onMove(double mX, double mY) {
        double x = mX + canvas.xLocation + radius;
        double y = mY + canvas.yLocation + radius;

        // pravá, dolní
        if (x < canvas.xLocation || y < canvas.yLocation
                || mX > canvas.fxcanvas.getWidth()
                || mY > canvas.fxcanvas.getHeight()) {

            cursor.setVisible(false);

        } else {
            cursor.setCenterX(Math.floor(x) + .5);
            cursor.setCenterY(Math.floor(y) + .5);
            cursor.setVisible(true);
        }
    }

    public int getRadius() {
        return radius;
    }

}