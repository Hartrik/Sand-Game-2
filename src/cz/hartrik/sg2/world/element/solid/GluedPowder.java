
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.powder.Powder;
import cz.hartrik.sg2.world.element.Metamorphic;

/**
 * Tento element obalí jiný práškový element a vytvoří z něj element statický.
 * Zachovává teplotní vlastnosti původního elementu.
 *
 * @version 2016-06-14
 * @author Patrik Harag
 * @param <E> typ obaleného elementu
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
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.testTemperature(x, y);
    }

    @Override
    public Color getColor() {
        return element.getColor().blend(Color.BLACK.changeAlpha(0.2));
    }

    @Override
    public boolean hasTemperature() {
        return element.hasTemperature();
    }

    @Override
    public boolean isConductive() {
        return element.isConductive();
    }

    @Override
    public float getConductiveIndex() {
        return element.getConductiveIndex();
    }

    @Override
    public float loss() {
        return element.loss();
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