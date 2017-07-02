package cz.hartrik.sg2.world.template;

import javafx.scene.image.Image;

/**
 * Rozhraní pro šablonu, která poskytuje náhled.
 * 
 * @version 2015-01-11
 * @author Patrik Harag
 */
public interface TemplateWPreview extends Template {
    
    /**
     * Vrátí obrázek, který slouží jako náhled před vložením šablony.
     * 
     * @return obrázek
     */
    public Image getImage();
    
}