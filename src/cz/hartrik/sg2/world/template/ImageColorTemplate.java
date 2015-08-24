package cz.hartrik.sg2.world.template;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.Inserter;
import java.util.function.Function;
import javafx.scene.image.Image;

/**
 * Šablona vytvořená podle obrázku, která je určena hlavně k nanášení jednoho
 * elementu různými barvami.
 * 
 * @version 2015-01-11
 * @author Patrik Harag
 */
public class ImageColorTemplate extends AImageTemplate {

    private final Function<Color, Element> function;

    /**
     * Vytvoří novou šablonu.
     * 
     * @param image obrázek, podle kterého se šablona vytvoří
     * @param function funkce vytvářející elementy na základě barvy
     */
    public ImageColorTemplate(Image image, Function<Color, Element> function) {

        super(image);
        this.function = function;
    }

    @Override
    protected void insertAt(int x, int y, Color color,
            Inserter<? extends ElementArea> inserter) {
        
        inserter.insert(x, y, function.apply(color));
    }
    
}