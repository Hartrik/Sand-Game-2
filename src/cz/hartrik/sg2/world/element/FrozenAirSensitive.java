
package cz.hartrik.sg2.world.element;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.random.Chance;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;

/**
 * Představuje jakoby zamrzlý element, který je po čase opět rozmrazen a
 * pokračuje v činnosti. Může být rozmražen jen tehdy, pokud je v jeho okolí
 * vzduch.
 * 
 * @version 2014-04-08
 * @author Patrik Harag
 */
public class FrozenAirSensitive extends Frozen {
    
    private static final long serialVersionUID = 83715083867368_02_014L;

    public FrozenAirSensitive(Element element, Chance chance) {
        super(element, chance);
    }
    
    public FrozenAirSensitive(Color color, Element element, Chance chance) {
        super(color, element, chance);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        boolean noAir = tools.getDirVisitor().visitWhileAll(x, y,
                (Element element) -> !(element instanceof Air));
        
        if (!noAir && chanceToUnfreeze.nextBoolean())
            world.setAndChange(x, y, frozenElement);
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getDirVisitor().testAll(x, y, element -> {
            return element instanceof Air;
        });
    }
    
}