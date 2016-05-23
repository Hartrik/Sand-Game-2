package cz.hartrik.sg2.engine;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Element;
import java.io.InputStream;
import java.util.function.Function;
import javafx.scene.image.Image;

/**
 * Obsahuje základní postupy pro vykreslování elementů.
 *
 * @version 2016-05-23
 * @author Patrik Harag
 */
public final class ColorPalettes {

    private ColorPalettes() { }

    public static final Function<Element, Color> NORMAL = Element::getColor;

    public static final Function<Element, Color> HEATMAP;
    static {
        InputStream is = ColorPalettes.class.getResourceAsStream("gradient.png");
        Image gradient = new Image(is);

        HeatmapColorPalette palette = new HeatmapColorPalette(gradient, 0, 2000);
        HEATMAP = palette::color;
    }

}