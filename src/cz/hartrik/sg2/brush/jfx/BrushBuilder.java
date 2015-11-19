
package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.common.Checker;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import javafx.scene.image.Image;

/**
 * Předek pro buildery jednotlivých typů štětců.
 *
 * @version 2015-11-19
 * @author Patrik Harag
 */
public abstract class BrushBuilder {

    private final BrushCollectionBuilder collectionBuilder;
    protected BrushInfo brushInfo;
    protected Image image;
    private boolean hidden = false;

    public BrushBuilder(BrushCollectionBuilder collectionBuilder) {
        this.collectionBuilder = collectionBuilder;
    }

    // metody

    public BrushBuilder setBrushInfo(BrushInfo brushInfo) {
        this.brushInfo = brushInfo;

        return this;
    }

    public BrushBuilder setImage(Image image) {
        this.image = image;
        return this;
    }

    public BrushBuilder setImage(String url) {
        this.image = new Image(url);
        return this;
    }

    public BrushBuilder hidden() {
        this.hidden = true;

        return this;
    }

    public BrushCollectionBuilder build() {
        Checker.requireNonNull(brushInfo, "brushInfo cannot be null");

        Brush brush = (image == null)
                ? Thumbnails.addGeneratedThumb(createBrush())
                : Thumbnails.addThumb(image, createBrush());

        return collectionBuilder.add(brush, hidden);
    }

    protected abstract Brush createBrush();

}