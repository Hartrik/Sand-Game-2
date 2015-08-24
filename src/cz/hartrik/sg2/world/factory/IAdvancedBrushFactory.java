
package cz.hartrik.sg2.world.factory;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;

/**
 * Továrna kompatibilní s metodou getElement třídy {@link Brush} s více
 * parametry.
 * 
 * @version 2014-12-20
 * @author Patrik Harag
 * @param <E> typ produkovaného elementu
 */
@FunctionalInterface
public interface IAdvancedBrushFactory<E extends Element> extends IElementFactory<E> {
    
    public E getElement(Element current, int x, int y, ElementArea area, Controls controls);
    
}