
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.fluid.EWater;
import cz.hartrik.sg2.world.element.powder.PowderMid;

/**
 * Element představující cement. Cement se musí setkat s wodou, aby mohl
 * následně postupně ztvrdnout.
 * 
 * @see EWater
 * @see CementWetFluid
 * @see CementWetSand
 * @see Concrete
 * 
 * @version 2014-04-09
 * @author Patrik Harag
 */
public class Cement extends PowderMid {

    private static final long serialVersionUID = 83715083867368_02_004L;
    
    private final CementWetFluid wetCement;
    
    public Cement(Color color, int density, CementWetFluid wetCement) {
        super(color, density);
        this.wetCement = wetCement;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        boolean noChange = tools.getDirectionVisitor().visitWhileAll(x, y,
                (Element element, int ex, int ey) -> {
            
            if (element instanceof EWater) {
                world.setAndChange(x, y, wetCement);
                world.setAndChange(ex, ey, world.getBackground());
                return false;
            }
            return true;
        });
        
        if (noChange) super.doAction(x, y, tools, world);
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return super.testAction(x, y, tools, world);
        // testovat přítomnost vody je zbytečné
    }
    
}