package cz.hartrik.sg2.world.template;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.Inserter;
import javafx.scene.image.Image;

/**
 * Šablona, která je vykreslována celá jedním štětcem. Vstupní obrázek by měl
 * být nejlépe černobílý. Štětec je nanesen pouze na místa, která odpovídají
 * {@link Color#BLACK}.
 * 
 * @version 2015-01-11
 * @author Patrik Harag
 */
public class ImageMonoTemplate extends AImageTemplate {

    private final Brush brush;

    /**
     * Vytvoří novou šablonu.
     * 
     * @param image obrázek, podle kterého se šablona vytvoří
     * @param brush štětec, který bude aplikován na místa, na kterých se v
     *              šabloně vyskytuje černá barva
     */
    public ImageMonoTemplate(Image image, Brush brush) {
        super(image);
        this.brush = brush;
    }

    @Override
    protected void insertAt(int x, int y, Color color,
            Inserter<? extends ElementArea> inserter) {
        
        final ElementArea area = inserter.getArea();
        
        if (!color.equals(Color.BLACK) || !area.valid(x, y)) return;
        
        final Element current = area.get(x, y);
        final Element element = brush.getElement(current, x, y, area, null);
        
        inserter.insert(x, y, element);
    }
    
}