
package cz.hartrik.sg2.world.element.fluid.simplewater;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.common.random.RatioChance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.fluid.EWater;
import cz.hartrik.sg2.world.element.powder.PowderMid;
import cz.hartrik.sg2.world.factory.ISingleInputFactory;

/**
 * Element představující sůl.
 * 
 * @version 2014-05-22
 * @author Patrik Harag
 */
public class Salt extends PowderMid {
    
    private static final long serialVersionUID = 83715083867368_02_022L;

    protected static final Chance SALT_RATIO = new RatioChance(7);
    
    private final ISingleInputFactory<Salt, EWaterSalt> saltWaterFactory;
    
    public Salt(Color color, int density,
            ISingleInputFactory<Salt, EWaterSalt> saltWaterFactory) {
        
        super(color, density);
        this.saltWaterFactory = saltWaterFactory;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (!wet(x, y, tools, world))
            super.doAction(x, y, tools, world);
    }
    
    protected boolean wet(int x, int y, Tools tools, World world) {
        return !tools.getDirectionVisitor().visitWhile(x, y,
                (Element element, int nX, int nY) -> {
                    
            if (element instanceof EWater && !(element instanceof EWaterSalt)) {
                if (SALT_RATIO.nextBoolean()) { // sůl mizí
                    world.setAndChange(x, y, saltWaterFactory.apply(this));
                    world.setAndChange(nX, nY, world.getBackground());
                } else { // sůl zůstává
                    world.setAndChange(nX, nY, saltWaterFactory.apply(this));
                }
                return false;
            }
            return true;
        }, tools.randomDirection());
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return super.testAction(x, y, tools, world);
        // testovat přítomnost vody je zbytečné
    }
    
}