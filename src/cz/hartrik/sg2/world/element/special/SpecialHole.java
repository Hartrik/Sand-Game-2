
package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import java.util.function.Predicate;

/**
 * Element pohlcující jiné elementy na základě predikátu vyhodnoceného jako
 * <code>true</code>.
 * 
 * @version 2014-08-31
 * @author Patrik Harag
 */
public class SpecialHole extends Hole {

    private static final long serialVersionUID = 83715083867368_02_064L;
    
    private final Predicate<Element> predicate;
    
    public SpecialHole(Color color, Predicate<Element> predicate) {
        super(color);
        this.predicate = predicate;
    }
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        tools.getDirectionVisitor().visitAll(x, y,
                (Element element, int eX, int eY) -> {

            if (predicate.test(element))
                world.setAndChange(eX, eY, world.getBackground());
        });
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getDirectionVisitor()
                .testAll(x, y, element -> predicate.test(element));
    }
    
}