
package cz.hartrik.sg2.app.extension.canvas;

import javafx.scene.shape.Rectangle;

/**
 * Vycentrovaný obdélníkový kurzor.
 * 
 * @version 2015-03-18
 * @author Patrik Harag
 */
public class RectangleCursorCentered extends RectangleCursor {

    public RectangleCursorCentered(CanvasWithCursor canvas, Rectangle rect) {
        super(canvas, rect);
    }

    public RectangleCursorCentered(CanvasWithCursor canvas, int w, int h) {
        super(canvas, w, h);
    }
    
    @Override
    public void onMove(double mX, double mY) {
        mX -= ((width  > 1) ? width  / 2 : 0);
        mY -= ((height > 1) ? height / 2 : 0);
        
        super.onMove(mX, mY);
    }

}