
package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.temperature.Thermal;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluenced;

/**
 * Element představující topné těleso.
 * 
 * @version 2014-05-15
 * @author Patrik Harag
 */
public class Heater extends SolidElement implements Thermal {
    
    private static final long serialVersionUID = 83715083867368_02_016L;

    private final Color color;
    private final int temp;

    private static final Direction[] DIRECTIONS = Direction.values();
    
    public Heater(Color color, int degrees) {
        this.color = color;
        this.temp = degrees;
    }
    
    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        tools.getDirectionVisitor().visit(x, y,
                (Element element, int nX, int nY) -> {
            tools.getFireTools().affect(nX, nY, element, temp, false);
        }, DIRECTIONS);
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getDirectionVisitor().test(x, y, (Element element) -> {
            return element instanceof ThermalInfluenced;
        }, DIRECTIONS);
    }

    @Override
    public int getTemperature() {
        return temp;
    }
    
}