
package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import java.util.function.Predicate;

/**
 * Element pohlcující jiné elementy na základě predikátu vyhodnoceného jako
 * <code>true</code>.
 *
 * @version 2016-06-14
 * @author Patrik Harag
 */
public class SmartHole extends Hole {

    private static final long serialVersionUID = 83715083867368_02_064L;

    private final Predicate<Element> predicate;

    public SmartHole(Color color, Predicate<Element> predicate) {
        super(color);
        this.predicate = predicate;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        tools.getDirVisitor().visitAll(x, y,
                (Element element, int eX, int eY) -> {

            if (predicate.test(element)) {
                world.setAndChange(eX, eY, world.getBackground());
                world.setTemperature(eX, eY, World.DEFAULT_TEMPERATURE);
            }
        });
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getDirVisitor()
                .testAll(x, y, element -> predicate.test(element));
    }

}