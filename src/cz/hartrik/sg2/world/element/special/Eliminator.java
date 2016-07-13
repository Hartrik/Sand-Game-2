
package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.powder.PowderLow;

/**
 * Představuje element, který když se přiblíží k jinému elementu, tak ho pohltí
 * a sám zanikne.
 *
 * @version 2016-06-14
 * @author Patrik Harag
 */
public class Eliminator extends PowderLow {

    private static final long serialVersionUID = 83715083867368_02_010L;

    public Eliminator(Color color) {
        super(color, Integer.MAX_VALUE);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (!eliminate(x, y, tools, world))
            super.doAction(x, y, tools, world);
    }

    protected boolean eliminate(int x, int y, Tools tools, World world) {
        return !tools.getDirVisitor().visitWhileAll(x, y,
                (Element element, int eX, int eY) -> {

            if (!(element instanceof Eliminator || element instanceof Air)) {
                // odstraní sám sebe a další element
                world.setAndChange(x, y, world.getBackground());
                world.setAndChange(eX, eY, world.getBackground());

                // odebere také teplotu odstraněného elementu
                world.setTemperature(eX, eY, World.DEFAULT_TEMPERATURE);

                // zastaví procházení
                return false;

            } else {
                // pokračuje v procházení
                return true;
            }
        });
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return super.testAction(x, y, tools, world)
                || testEliminate(x, y, tools, world);
    }

    protected boolean testEliminate(int x, int y, Tools tools, World world) {
        return tools.getDirVisitor().testAll(x, y, element -> {
            return !(element instanceof Eliminator || element instanceof Air);
        });
    }

    // Glueable

    @Override
    public Element glue() {
        return null;
    }

}