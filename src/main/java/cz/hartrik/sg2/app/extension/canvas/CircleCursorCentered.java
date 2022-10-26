package cz.hartrik.sg2.app.extension.canvas;

import javafx.scene.shape.Circle;

/**
 * @version 2015-03-19
 * @author Patrik Harag
 */
public class CircleCursorCentered extends CircleCursor {

    public CircleCursorCentered(CanvasWithCursor zoomable, Circle circle) {
        super(zoomable, circle);
    }

    public CircleCursorCentered(CanvasWithCursor zoomable, int radius) {
        super(zoomable, radius);
    }

    @Override
    public void onMove(double mX, double mY) {
        super.onMove(mX - getRadius(), mY - getRadius());
    }
    
}