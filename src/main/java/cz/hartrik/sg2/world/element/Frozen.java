package cz.hartrik.sg2.world.element;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.random.Chance;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;

/**
 * Představuje jakoby zamrzlý element, který je po čase opět rozmrazen a
 * pokračuje v činnosti. Nemá nic společného s teplotou.
 * 
 * @version 2014-04-08
 * @author Patrik Harag
 */
public class Frozen extends SolidElement implements Container<Element> {
    
    private static final long serialVersionUID = 83715083867368_02_013L;
    
    private final Color color;
    protected final Chance chanceToUnfreeze;
    protected final Element frozenElement;

    public Frozen(Element element, Chance chance) {
        this(element.getColor(), element, chance);
    }
    
    public Frozen(Color color, Element element, Chance chance) {
        this.color = color;
        this.chanceToUnfreeze = chance;
        this.frozenElement = element;
    }

    @Override public Element getElement() { return frozenElement; }
    @Override public Color getColor()     { return color; }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        boolean allFrozen = tools.getDirVisitor().visitWhileAll(x, y,
                (Element element) -> element instanceof Frozen);
        
        if (!allFrozen && chanceToUnfreeze.nextBoolean())
            world.setAndChange(x, y, frozenElement);
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getDirVisitor().testAll(x, y, element -> {
            return !(element instanceof Frozen);
        });
    }
    
}