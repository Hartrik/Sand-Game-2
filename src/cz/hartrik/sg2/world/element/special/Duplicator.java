
package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.powder.PowderLow;

/**
 * Element představující duplikátor - při kontaktu s jiným elementem ho
 * duplikuje.
 *
 * @version 2014-12-28
 * @author Patrik Harag
 */
public class Duplicator extends PowderLow {

    private static final long serialVersionUID = 83715083867368_02_050L;

    public Duplicator(Color color) {
        super(color, Integer.MAX_VALUE);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (!duplicate(x, y, tools, world))
            super.doAction(x, y, tools, world);
    }

    protected boolean duplicate(int x, int y, Tools tools, World world) {
        return !tools.getDirVisitor().visitWhileAll(x, y, element -> {
            if (!(element instanceof Duplicator || element instanceof Air)) {
                Brush producer = getProducer(tools, element);

                if (producer != null) {
                    Element prod = producer.getElement(null, x, y, world, null);
                    if (prod != null)
                        world.setAndChange(x,  y, prod);
                    else
                        world.setAndChange(x,  y, element);
                } else
                    world.setAndChange(x,  y, element);

                return false;
            } else
                return true;
        });
    }

    protected Brush getProducer(Tools tools, Element element) {
        return tools.getBrushManager().getProducerAll(element);
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return super.testAction(x, y, tools, world)
                || testDuplicate(x, y, tools, world);
    }

    protected boolean testDuplicate(int x, int y, Tools tools, World world) {
        return tools.getDirVisitor().testAll(x, y, element -> {
            return !(element instanceof Duplicator || element instanceof Air);
        });
    }

    // Glueable

    @Override
    public Element glue() {
        return null;
    }

}