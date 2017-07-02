package cz.hartrik.sg2.world.template;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.world.Inserter;
import java.util.Map;
import javafx.scene.image.Image;

/**
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class ImageMapTemplate extends AImageTemplate {

    private final Map<Color, Brush> map;
    private final Brush def;

    public ImageMapTemplate(Image image, Map<Color, Brush> map, Brush def) {
        super(image);

        this.map = map;
        this.def = def;
    }

    @Override
    protected void insertAt(int x, int y, Color color, Inserter<?> inserter) {
        Brush brush = map.getOrDefault(color, def);
        inserter.apply(x, y, brush);
    }

}