
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.powder.Powder;
import cz.hartrik.sg2.world.element.type.Destructible;
import cz.hartrik.sg2.world.element.type.Metamorphic;

/**
 * 
 * 
 * @version 2014-12-26
 * @author Patrik Harag
 * @param <E>
 */
public class GluedPowder<E extends Powder> extends SolidElement
        implements Metamorphic<E>, Destructible {

    private static final long serialVersionUID = 83715083867368_02_065L; 

    private final E element;

    public GluedPowder(E element) {
        this.element = element;
    }

    // Element

    @Override
    public Color getColor() {
        return element.getColor().blend(Color.BLACK.changeAlpha(0.2));
    }

    // Metamorphic

    @Override
    public E getBasicElement() {
        return element;
    }

    // Destructible
    
    @Override
    public Element destroy() {
        return getBasicElement();
    }

}