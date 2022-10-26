package cz.hartrik.sg2.world.template;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.Image;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.Inserter;

/**
 * Abstraktní třída se základem pro další šablony vytvářené z obrázků.
 * 
 * @version 2017-08-06
 * @author Patrik Harag
 */
public abstract class AImageTemplate implements Template {
    
    private final Image image;

    public AImageTemplate(Image image) {
        this.image = image;
    }

    protected abstract void insertAt(
            int x, int y, Color color, Inserter<? extends ElementArea> inserter);

    @Override
    public void insert(Inserter<? extends ElementArea> inserter, int x, int y) {
        for (int iy = 0; iy < getHeight(); iy++) {
            for (int ix = 0; ix < getWidth(); ix++) {
                Color color = image.getColor(ix, iy);
                insertAt(x + ix, y + iy, color, inserter);
            }
        }
    }
    
    /**
     * Vrátí obrázek, podle kterého byla šablona vytvořena.
     * 
     * @return obrázek
     */
    @Override
    public Image getImage() {
        return image;
    }
    
    @Override
    public boolean isResponsive() {
        return false;
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }
    
}