
package cz.hartrik.sg2.app.module.canvas;

import javafx.scene.shape.Line;

/**
 * Vytvoří přímku, která se bude napínat od počátečního bodu k aktuální pozici
 * kurzoru.
 *
 * @version 2015-03-20
 * @author Patrik Harag
 */
public class LineIndicator extends ANodeCursor<Line> {

    // k pozicím přímky se připočítává 0.5 aby se umístila
    // přesně do pixelu, ne na jeho hranu

    public LineIndicator(CanvasWithCursor canvas, double initX, double initY) {
        super(canvas, createLine(
                Math.floor(initX + canvas.xLocation) + .5,
                Math.floor(initY + canvas.yLocation) + .5));
    }

    private static Line createLine(double initX, double initY) {
        Line line = new Line(initX, initY, initX, initY);
        line.setSmooth(false);
        return line;
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
        cursor.setEndX(Math.floor(mX + canvas.xLocation) + .5);
        cursor.setEndY(Math.floor(mY + canvas.yLocation) + .5);

        if (mX < 0 || mY < 0
                || mX > canvas.fxcanvas.getWidth()
                || mY > canvas.fxcanvas.getHeight()) {

            cursor.setVisible(false);

        } else {
            cursor.setVisible(true);
        }
    }

}
