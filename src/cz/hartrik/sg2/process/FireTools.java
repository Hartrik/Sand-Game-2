
package cz.hartrik.sg2.process;

import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.temperature.Flammable;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluenced;
import cz.hartrik.sg2.world.factory.IFireFactory;

/**
 * Nástroje pro práci s ohněm a teplotou.
 * 
 * @see Flammable
 * @see ThermalInfluenced
 * 
 * @version 2014-05-15
 * @author Patrik Harag
 */
public class FireTools {
    
    private final World world;
    private final Tools tools;
    private final IFireFactory fireFactory;

    public FireTools(World world, Tools tools, IFireFactory fireFactory) {
        this.world = world;
        this.tools = tools;
        this.fireFactory = fireFactory;
    }
    
    public IFireFactory getFireFactory() {
        return fireFactory;
    }
    
    public boolean findAir(int x, int y) {
        return !tools.getDirectionVisitor().visitWhileAll(x, y,
                (Element element) -> !(element instanceof Air));
    }
    
    // affect
    
    public boolean affect(int x, int y, int temperature, boolean fire) {
        return world.valid(x, y)
                ? affect(x, y, world.get(x, y), temperature, fire)
                : false;
    }
    
    /**
     * Vyvyne teplotu na určitý element. Pokud je element hořlavý, může se
     * vznítit.
     * 
     * @param x horizontální pozice elementu
     * @param y vertikální pozice elementu
     * @param element element
     * @param temperature teplota působící na e
     * @param fire přítomnost ohně
     * @return pokud je element zapálen nebo nahrazen jiným
     */
    public boolean affect(int x, int y, Element element, int temperature,
            boolean fire) {
        
        return element instanceof Flammable
                ? (affectFlammable(x, y, (Flammable) element, temperature, fire)
                    ? true
                    : affectTI(x, y, element, temperature, fire))
                : affectTI(x, y, element, temperature, fire);
    }
    
    private boolean affectTI(int x, int y, Element element, int temperature,
            boolean fire) {
        
        return element instanceof ThermalInfluenced
                ? affectTI(x, y, (ThermalInfluenced) element, temperature, fire)
                : false;
    }
    
    /**
     * Vyvyne teplotu na hořlavý element. Pokud je v okolí vzduch, tak se může
     * vznítit.
     * 
     * @param x horizontální pozice elementu
     * @param y vertikální pozice elementu
     * @param flammable hořlavý element
     * @param temperature teplota působící na element
     * @param fire přítomnost ohně
     * @return došlo ke vznícení
     */
    public boolean affectFlammable(int x, int y, Flammable flammable,
            int temperature, boolean fire) {
        
        if ((fire || findAir(x, y))
                && temperature >= flammable.getDegreeOfFlammability()
                && flammable.getChanceToFlareUp()) {
            
            world.setAndChange(x, y, fireFactory.getFireFocus(flammable));
            return true;
        }
        return false;
    }

    /**
     * Vyvyne teplotu na element.
     * 
     * @param x horizontální pozice elementu
     * @param y vertikální pozice elementu
     * @param element element, který může být ovlivněn ohněm
     * @param temperature teplota působící na element
     * @param fire přítomnost ohně
     * @return došlo ke spálení nebo nahrazení elementu jiným
     */
    public boolean affectTI(int x, int y, ThermalInfluenced element,
            int temperature, boolean fire) {
        
        return element.temperature(x, y, tools, world, temperature, fire);
    }
    
}