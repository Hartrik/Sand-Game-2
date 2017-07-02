package cz.hartrik.sg2.world.template;

/**
 * Rozhraní pro responsivní šablonu ke snadnější implementaci.
 * 
 * @version 2014-11-30
 * @author Patrik Harag
 */
public interface ResponsiveTemplate extends Template {

    @Override
    public default boolean isResponsive() {
        return true;
    }
    
    @Override
    public default int getWidth() {
        return -1;
    }
    
    @Override
    public default int getHeight() {
        return -1;
    }
    
}