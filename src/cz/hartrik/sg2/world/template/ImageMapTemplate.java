package cz.hartrik.sg2.world.template;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.Inserter;
import java.util.Map;
import javafx.scene.image.Image;

/**
 * @version 2015-01-11
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
    protected void insertAt(int x, int y, Color color,
            Inserter<? extends ElementArea> inserter) {
        
        if (!inserter.getArea().valid(x, y)) return;
        
        final Brush brush = map.getOrDefault(color, def);
        final Element element = brush.getElement(null, x, y, inserter.getArea(), null);
        
        inserter.insert(x, y, element);
    }
    
}