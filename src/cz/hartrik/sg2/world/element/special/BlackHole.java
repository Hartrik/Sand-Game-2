
package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;

/**
 * Element představující "černou díru". Pohltí všechny ostatní elementy, které
 * se vyskytnou v její blízkosti.
 *
 * @version 2016-06-14
 * @author Patrik Harag
 */
public class BlackHole extends Hole implements Sourceable {

    private static final long serialVersionUID = 83715083867368_02_002L;

    public BlackHole(Color color) {
        super(color);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        tools.getDirVisitor().visitAll(x, y,
                (Element element, int eX, int eY) -> {

            if (!(element instanceof BlackHole || element instanceof Air)) {
                world.setAndChange(eX, eY, world.getBackground());
                world.setTemperature(eX, eY, World.DEFAULT_TEMPERATURE);
            }
        });
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getDirVisitor().testAll(x, y, element -> {
            return !(element instanceof BlackHole || element instanceof Air);
        });
    }

}