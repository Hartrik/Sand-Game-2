package cz.hartrik.sg2.app.extension.canvas;

import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Kurzor vytvořený z obrázku.
 *
 * @version 2015-03-20
 * @author Patrik Harag
 */
public class ImageCursor extends ARectangularCursor<ImageView> {

    public ImageCursor(CanvasWithCursor zoomable, Image image) {
        super(zoomable, new ImageView(image),
                (int) image.getWidth(), (int) image.getHeight());
    }

    // Cursor

    @Override
    public void addCursor() {
        cursor.setVisible(false);
        canvas.fxcanvas.setCursor(Cursor.OPEN_HAND);  // !
        canvas.zoomGroup.getChildren().add(cursor);
    }

    @Override
    public void onMove(double mX, double mY) {
        double x = mX + canvas.xLocation - ((width  > 1) ? width  / 2 : 0);
        double y = mY + canvas.yLocation - ((height > 1) ? height / 2 : 0);

        cursor.setFitWidth(width);
        cursor.setFitHeight(height);

        double w = width, h = height;
        double vX = 0, vY = 0, vW = width, vH = height;

        // levá
        if (x < canvas.xLocation) {
            w =width - canvas.xLocation + x;
            x = canvas.xLocation;
            vX = width - w;
            vW = w;
        }

        // horní
        if (y < canvas.yLocation) {
            h = height - canvas.yLocation + y;
            y = canvas.yLocation;
            vY = height - h;
            vH = h;
        }

        // pravá, dolní
        double rBound = canvas.xLocation + canvas.fxcanvas.getWidth();
        double dBound = canvas.yLocation + canvas.fxcanvas.getHeight();

        if (x + width  > rBound) {
            w = rBound - x;
            vW = w;
        }

        if (y + height > dBound){
            h = dBound - y;
            vH = h;
        }

        cursor.setFitWidth(Math.floor(w));
        cursor.setFitHeight(Math.floor(h));
        cursor.setViewport(new Rectangle2D(Math.floor(vX), Math.floor(vY),
                                           Math.floor(vW), Math.floor(vH)));
        cursor.setX(Math.floor(x));
        cursor.setY(Math.floor(y));
        cursor.setVisible(true);
    }

}