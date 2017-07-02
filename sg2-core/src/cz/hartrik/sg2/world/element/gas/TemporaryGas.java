
package cz.hartrik.sg2.world.element.gas;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.random.Chance;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Container;

/**
 * Představuje plynný element, které se po čase přemění na element jiný.
 *
 * @version 2014-04-10
 * @author Patrik Harag
 * @param <E> element
 */
public class TemporaryGas<E extends Element> extends SimpleGasElement
        implements Container<E> {

    private static final long serialVersionUID = 83715083867368_02_048L;

    protected final E element;
    protected final Chance chanceToUnwrap;

    public TemporaryGas(Color color, int density, Chance chanceToMoveUp,
            E element, Chance chanceToUnwrap) {

        super(color, density, chanceToMoveUp);
        this.element = element;
        this.chanceToUnwrap = chanceToUnwrap;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (!unwrap(x, y, tools, world))
            super.doAction(x, y, tools, world);
    }

    protected boolean unwrap(int x, int y, Tools tools, World world) {
        if (chanceToUnwrap.nextBoolean()) {
            world.setAndChange(x, y, element);
            return true;
        } else return false;
    }

    @Override
    public E getElement() {
        return element;
    }

}