
package cz.hartrik.sg2.world.element.temperature;

import cz.hartrik.sg2.process.Optimizer;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;

/**
 * Za pomoci <code>default</code> metod do cílové třídy implementuje všechny
 * nástroje potřebné pro šíření tepla. <br>
 * <i>Je to možná trochu proti OOP, ale
 * zato pohodlný způsob, jak dostat tuto logiku do více tříd.</i>
 * 
 * @version 2014-05-20
 * @author Patrik Harag
 */
public interface ThermalConductiveDef
        extends ThermalConductive, ThermalInfluenced  {

    /**
     * Index v rozmezí 0 - 0.5,
     * při indexu 0 neodvede z jiných elementů žádné teplo,
     * při indexu 0.5 odvede až polovinu tepla.
     * 
     * @return index
     */
    public float getConductiveIndex();
    
    @Override
    public default int conductTemperature(int temp) {
        final int temperature = getTemperature();
        
        if (temperature == temp) return 0;
        
        final int change = temperature > temp
                ? - (int) ((temperature - temp) * getConductiveIndex())
                :   (int) ((temp - temperature) * getConductiveIndex());
        
        setTemperature(temperature + change);
        return change;
    }
    
    @Override
    public default boolean temperature(int x, int y, Tools tools, World world,
            int degrees, boolean fire) {
        
        if (getTemperature() < degrees)
            setTemperature(getTemperature()
                    + (int) ((degrees - getTemperature()) * getConductiveIndex()));
        return false;
    }
    
//    default void affectNear(int x, int y, Tools tools, World world) {
//        if (getTemperature() > NORMAL_TEMP) {
//            setTemperature(decrementTemperature(getTemperature()));
//            
//            tools.getDirectionVisitor().visit(x, y,
//                    (Element next, int eX, int eY) -> {
//
//                if (next instanceof ThermalConductive) {
//                    final int change = ((ThermalConductive) next)
//                            .conductTemperature(getTemperature());
//                    
//                    final int last = getTemperature();
//                    setTemperature(last - change);
//                    
//                    if (next instanceof Flammable)
//                        tools.getFireTools().affectFlammable(
//                                eX, eY, (Flammable) next, last, false);
//                } else {
//                    tools.getFireTools()
//                            .affect(eX, eY, next, getTemperature(), false);
//                }
//            }, tools.randomDirection());
//        }
//    }
    
    default int decrementTemperature(int value) {
        return value - 1;
    }
    
    
    // optimalizace
    
    default void affectNear(int x, int y, Tools tools, World world) {
        final int temp1 = getTemperature();
        if (temp1 > NORMAL_TEMP) {
            final int temp2 = decrementTemperature(temp1);
            setTemperature(temp2);
            
            final int rnd = tools.randomInt(4);
            final int eX = x + Optimizer.POSITIONS_MAIN[rnd];
            final int eY = y + Optimizer.POSITIONS_MAIN[rnd + 1];
            
            if (world.valid(eX, eY)) {
                final Element next = world.get(eX, eY);
                
                if (next instanceof ThermalConductive) {
                    final int change = ((ThermalConductive) next)
                            .conductTemperature(temp2);
                    
                    setTemperature(temp2 - change);
                    
                    if (next instanceof Flammable)
                        tools.getFireTools().affectFlammable(
                                eX, eY, (Flammable) next, temp2, false);
                } else {
                    tools.getFireTools()
                            .affect(eX, eY, next, temp2, false);
                }
            }
        }
    }
    
}