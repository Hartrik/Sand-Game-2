
package cz.hartrik.sg2.app.extension.canvas;

import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

/**
 * Umožňuje přiblížení a centerování komponenty {@link ImageView} uvnitř
 * {@link ScrollPane}.
 * Při změně rozměrů jedné z komponent dojde automaticky k aktualizaci
 * zavoláním metody {@link #update() update()}.
 *
 * @version 2014-04-12
 * @author Patrik Harags
 */
public class SGCanvas {

    protected final ScrollPane scrollPane;
    protected final Canvas fxcanvas;

    protected final Scale scaleTransform;
    protected final Rectangle background;
    protected final Group zoomGroup;
    protected final Group contentGroup;

    protected double scale = 1.0;
    protected int minBounds = 10;
    protected double xLocation = minBounds;
    protected double yLocation = minBounds;

    public SGCanvas(ScrollPane scrollPane, Canvas canvas) {
        this.scrollPane = scrollPane;
        this.fxcanvas = canvas;

        this.zoomGroup = new Group();
        this.scaleTransform = new Scale(1, 1, 0, 0);
        zoomGroup.getTransforms().add(scaleTransform);
        zoomGroup.getChildren().add(canvas);

        this.contentGroup = new Group();
        this.background = new Rectangle(0, 0, 0, 0);
        background.setOpacity(0);
        contentGroup.getChildren().add(background);
        contentGroup.getChildren().add(zoomGroup);

        scrollPane.setContent(contentGroup);
        initListeners();
    }

    protected void initListeners() {
        ChangeListener<Number> listener = (ov, oldVal, newVal) -> update();

        scrollPane.widthProperty().addListener(listener);
        scrollPane.heightProperty().addListener(listener);
        fxcanvas.heightProperty().addListener(listener);
        fxcanvas.widthProperty().addListener(listener);
    }

    public Canvas getFxCanvas() {
        return fxcanvas;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public double getScale()  {
        return scale;
    }

    public int getMinBounds() {
        return minBounds;
    }

    /**
     * Nastaví novou úroveň přiblížení. Nová hodnota bude použita až při další
     * aktualizaci metodou {@link #update() update()}.
     *
     * @param scale přiblížení (1.0 = normální; 2.0 = dvojnásobné...)
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setMinBounds(int minBounds) {
        this.minBounds = minBounds;
    }

    /**
     * Nastaví nové přiblížení a aktualizuje.
     *
     * @see #update() update()
     * @see #setScale(double) setScale(double)
     * @param scale přiblížení
     */
    public void update(double scale) {
        setScale(scale);
        update();
    }

    /**
     * Aktualizuje pozici a přiblížení {@link ImageView} uvnitř
     * {@link ScrollPane}.
     */
    public void update() {
        final double hvalue = scrollPane.getHvalue();
        final double vvalue = scrollPane.getVvalue();

        final double width  = scrollPane.getWidth();
        final double height = scrollPane.getHeight();
        final double imageWidth  = fxcanvas.getWidth();
        final double imageHeight = fxcanvas.getHeight();

        final double minWidht  = (imageWidth + 2 * minBounds) * scale;
        final double minHeight = (imageHeight + 2 * minBounds) * scale;

        if (height > minHeight) {
            yLocation = (height / 2 - imageHeight / 2) / scale
                        - ((scale - 1) * imageHeight / 2 / scale);
            background.setHeight(height - 2);
        } else {
            yLocation = minBounds;
            background.setHeight(minHeight);
        }

        if (width > minWidht) {
            xLocation = (width / 2 - imageWidth / 2) / scale
                        - ((scale - 1) * imageWidth / 2 / scale);
            background.setWidth(width - 2);
        } else {
            xLocation = minBounds;
            background.setWidth(minWidht);
        }

        fxcanvas.relocate(xLocation, yLocation);

        scaleTransform.setX(scale);
        scaleTransform.setY(scale);

        scrollPane.setHvalue(hvalue);
        scrollPane.setVvalue(vvalue);
    }

}