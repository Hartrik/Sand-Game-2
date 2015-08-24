
package cz.hartrik.sg2.world.element.temperature;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.type.Container;
import cz.hartrik.sg2.world.factory.IFireFactory;

/**
 * Element představující statické ohnisko.
 * 
 * @version 2014-04-07
 * @author Patrik Harag
 */
public class FireFocus extends FireElement implements Container<Flammable> {
    
    private static final long serialVersionUID = 83715083867368_02_040L;

    protected final IFireFactory factory;
    protected final Flammable element;
    
    private final boolean isFireInfluenced;
    private final ThermalInfluenced fireInfluenced;
    private boolean haveAir;

    public FireFocus(Flammable element, IFireFactory factory) {
        this.element = element;
        this.factory = factory;
        this.isFireInfluenced = element instanceof ThermalInfluenced;
        this.fireInfluenced = isFireInfluenced
                ? (ThermalInfluenced) element : null;
    }
    
    // Element
    
    @Override
    public final Color getColor() {
        return element.getColor();
    }
    
    @Override
    public final int getDensity() {
        return element.getDensity();
    }
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        haveAir = false;
        
        tools.getDirectionVisitor().visitAll(x, y,
                (Element next, int eX, int eY) -> {
            
            if (next instanceof Air) {
                haveAir = true;
                world.setAndChange(eX, eY,
                        factory.getFire(element.getFlammableNumber()));
            } else
                if (next instanceof Flammable)
                    diffuse((Flammable) next, eX, eY, tools, world);
            
            if (next instanceof Fire) haveAir = true;
        });
        
        if (!haveAir) { extinguish(x, y, world); return; }  // uhasit
        
        burnElement(x, y, tools, world);
    }

    protected void diffuse(Flammable flam, int x, int y, Tools tools, World world) {
        if (element.getFlammableNumber() > flam.getDegreeOfFlammability()
                && flam.getChanceToFlareUp()) {

            boolean notFound = tools.getDirectionVisitor().visitWhileAll(x, y,
                    next -> !(next instanceof Air || next instanceof Fire));

            if (!notFound)
                world.setAndChange(x, y, factory.getFireFocus(flam));
        }
    }
    
    protected final boolean burnElement(int x, int y, Tools tools, World world) {
        if (isFireInfluenced)
            return fireInfluenced
                    .temperature(x, y, tools, world, getTemperature(), true);
        return false;
    }
    
    protected void extinguish(int x, int y, World world) {
        world.setAndChange(x, y, element);
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return true;
    }

    // Thermal
    
    @Override
    public int getTemperature() {
        return element.getFlammableNumber();
    }
    
    // Container
    
    @Override
    public Flammable getElement() {
        return element;
    }
    
}