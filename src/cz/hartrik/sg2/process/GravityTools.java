
package cz.hartrik.sg2.process;

import cz.hartrik.common.reflect.TODO;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.NonSolidElement;
import cz.hartrik.sg2.world.element.gas.Gas;
import cz.hartrik.sg2.world.element.powder.Powder;

/**
 * Nástroje pro přesouvání elementu.
 * 
 * @version 2014-04-24
 * @author Patrik Harag
 */
@TODO("Chybí metoda test hover")
public class GravityTools {
    
    private final World world;
    private final Tools tools;

    public GravityTools(World world, Tools tools) {
        this.world = world;
        this.tools = tools;
    }
    
    // move
    
    public final boolean move(
            final int x1, final int y1, final NonSolidElement e1,
            final int x2, final int y2) {
        
        return move(x1, y1, e1, x2, y2, world.get(x2, y2));
    }
    
    public final boolean move(
            final int x1, final int y1, final NonSolidElement e1,
            final int x2, final int y2, final Element e2) {
        
        if (e2 instanceof NonSolidElement) {
            final int diff = e1.getDensity() - e2.getDensity();
            return swap(x1, y1, e1, x2, y2, e2, diff);
        }
        return false;
    }
    
    public final boolean moveIfValid(
            final int x1, final int y1, final NonSolidElement e1,
            final int x2, final int y2) {
        
        return world.valid(x2, y2)
                && move(x1, y1, e1, x2, y2, world.get(x2, y2));
    }
    
    public final boolean moveIfValid(
            final int x1, final int y1, final NonSolidElement e1,
            final int x2, final int y2, final Element e2) {
        
        return world.valid(x2, y2) && move(x1, y1, e1, x2, y2, e2);
    }
    
    // test move
    
    public final boolean testMove(
            final NonSolidElement e1, final int x2, final int y2) {
        
        final Element e2 = world.get(x2, y2);
        return e2 instanceof NonSolidElement && e2.getDensity() < e1.getDensity();
    }
    
    public final boolean testMove(final NonSolidElement e1, final Element e2) {
        return e2 instanceof NonSolidElement && e2.getDensity() < e1.getDensity();
    }
    
    public final boolean testMoveAndValid(
            final NonSolidElement e1, final int x2, final int y2) {
        
        return world.valid(x2, y2) && testMove(e1, x2, y2);
    }
    
    // move sand
    
    public final boolean move(
            final int x1, final int y1, final Powder e1,
            final int x2, final int y2) {
        
        return move(x1, y1, e1, x2, y2, world.get(x2, y2));
    }
    
    public final boolean move(
            final int x1, final int y1, final Powder e1,
            final int x2, final int y2, final Element e2) {
        
        if (e2 instanceof NonSolidElement && !(e2 instanceof Powder)) {
            final int diff = e1.getDensity() - e2.getDensity();
            return swap(x1, y1, e1, x2, y2, e2, diff);
        }
        return false;
    }
    
    public final boolean moveIfValid(
            final int x1, final int y1, final Powder e1,
            final int x2, final int y2) {
        
        return world.valid(x2, y2)
                && move(x1, y1, e1, x2, y2, world.get(x2, y2));
    }
    
    public final boolean moveIfValid(
            final int x1, final int y1, final Powder e1,
            final int x2, final int y2, final Element e2) {
        
        return world.valid(x2, y2) && move(x1, y1, e1, x2, y2, e2);
    }
    
    // test move sand
    
    public final boolean testMoveAndValid(
            final Powder e1, final int x2, final int y2) {
        
        return world.valid(x2, y2) && testMove(e1, x2, y2);
    }
    
    public final boolean testMove(
            final Powder e1, final int x2, final int y2) {
        return testMove(e1, world.get(x2, y2));
    }
    
    public final boolean testMove(final Powder e1, final Element e2) {
        return e2 instanceof NonSolidElement && !(e2 instanceof Powder)
                && (e2.getDensity() < e1.getDensity());
    }
    
    // hover
    
    public final boolean hoverAuto(final int x1, final int y1, final Gas gas,
            final int x2) {
        
        return hoverAuto(x1, y1, gas, x2, (y1 + 1), (y1 - 1));
    }
    
    public final boolean hoverAuto(
            final int x1, final int y1, final Gas gas,
            final int x2, final int y2a, final int y2b) {
        
        final boolean valid1 = world.valid(x2, y2a);
        final boolean valid2 = world.valid(x2, y2b);
        
        return (valid1 && valid2)
                ? hoverAuto(x1, y1, gas, x2,
                    y2a, world.get(x2, y2a),
                    y2b, world.get(x2, y2b))
                : (valid1 && hover(x1, y1, gas, x2, y2a))
                    || (valid2 && hover(x1, y1, gas, x2, y2b));
    }
    
    public final boolean hoverAuto(
            final int x1, final int y1, final Gas gas, final int x2,
            final int y2a, final Element elementA,
            final int y2b, final Element elementB) {
        
        final boolean movable1 = elementA instanceof NonSolidElement;
        final boolean movable2 = elementB instanceof NonSolidElement;
        
        if (movable1 && movable2)
            return (gas.getDensity() > 0)
                    ? hover(x1, y1, gas, x2, y2a, elementA)
                    : hover(x1, y1, gas, x2, y2b, elementB);
        else
            return (movable1 && hover(x1, y1, gas, x2, y2a, elementA))
                || (movable2 && hover(x1, y1, gas, x2, y2b, elementB));
    }
    
    public final boolean hoverIfValid(
            final int x1, final int y1, final Gas gas,
            final int x2, final int y2) {
        
        return world.valid(x2, y2)
                && hover(x1, y1, gas, x2, y2, world.get(x2, y2));
    }
    
    public final boolean hover(
            final int x1, final int y1, final Gas gas,
            final int x2, final int y2) {
        
        return hover(x1, y1, gas, x2, y2, world.get(x2, y2));
    }
    
    public final boolean hover(
            final int x1, final int y1, final Gas gas,
            final int x2, final int y2, final Element element) {
        
        if (element instanceof NonSolidElement
                && (gas.getDensity() > element.getDensity()
                    || (element instanceof Air
                        && gas.getDensity() < element.getDensity()))) {
            
            final int diff = Math.abs(gas.getDensity() - element.getDensity());
            return swap(x1, y1, gas, x2, y2, element, diff);
        }
        return false;
    }
    
    // [test hover]
    
    // společné
    
    private boolean swap(final int x1, final int y1, final Element e1,
                         final int x2, final int y2, final Element e2,
                         final int diff) {
        
        if (diff > 99) {
            tools.swap(x1, y1, e1, x2, y2, e2);
            return true;
        } else if (diff > 0 && (tools.randomInt(100 - diff) == 0)) {
            tools.swap(x1, y1, e1, x2, y2, e2);
            return true;
        }
        return false;
    }
    
}