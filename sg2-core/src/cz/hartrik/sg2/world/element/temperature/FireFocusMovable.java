
package cz.hartrik.sg2.world.element.temperature;

import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.factory.IFireFactory;

/**
 * Element představující přemístitelné ohnisko.
 *
 * @version 2014-04-01
 * @author Patrik Harag
 */
public class FireFocusMovable extends FireFocus {

    private static final long serialVersionUID = 83715083867368_02_041L;

    private boolean lastMoved = true;

    public FireFocusMovable(Flammable element, IFireFactory factory) {
        super(element, factory);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (lastMoved) if (burnElement(x, y, tools, world)) return;

        world.setTemperature(x, y, element.getFlammableNumber());

        element.doAction(x, y, tools, world);

        Element elementAt = world.get(x, y);
        if (elementAt == this) {
            lastMoved = false;
            super.doAction(x, y, tools, world);
        } else {
            lastMoved = true;
            elementMoved(elementAt, x, y, tools, world);
        }
    }

    protected void elementMoved(Element newElement, int x, int y,
            Tools tools, World world) {

        tools.getDirVisitor().visitAll(x, y,
                (Element next, int eX, int eY) -> {

            if (next instanceof Air)
                world.setAndChange(eX, eY,
                        factory.getFire(element.getFlammableNumber()));
        });
    }

}