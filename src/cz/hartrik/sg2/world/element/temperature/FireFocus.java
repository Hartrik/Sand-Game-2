
package cz.hartrik.sg2.world.element.temperature;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.Container;
import cz.hartrik.sg2.world.factory.IFireFactory;

/**
 * Element představující statické ohnisko.
 *
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class FireFocus extends FireElement implements Container<Flammable> {

    private static final long serialVersionUID = 83715083867368_02_040L;

    protected final IFireFactory factory;
    protected final Flammable element;

    private final boolean isFireInfluenced;
    private final Burnable fireInfluenced;
    private boolean airFound;

    public FireFocus(Flammable element, IFireFactory factory) {
        this.element = element;
        this.factory = factory;
        this.isFireInfluenced = element instanceof Burnable;
        this.fireInfluenced = isFireInfluenced ? (Burnable) element : null;
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
        airFound = false;

        world.setTemperature(x, y, element.getFlammableNumber());

        tools.getDirVisitor().visitAll(x, y, (Element next, int eX, int eY) -> {
            if (next instanceof Air) {
                airFound = true;
                world.setAndChange(eX, eY, factory.getFire(element.getFlammableNumber()));
                world.setTemperature(eX, eY, element.getFlammableNumber());
            } else
                if (next instanceof Flammable)
                    diffuse((Flammable) next, eX, eY, tools, world);

            if (next instanceof Fire) airFound = true;
        });

        if (!airFound) {
            extinguish(x, y, world);
            return;
        }

        burnElement(x, y, tools, world);
    }

    protected void diffuse(Flammable flam, int x, int y, Tools tools, World world) {
        if (element.getFlammableNumber() > flam.getDegreeOfFlammability()
                && flam.getChanceToFlareUp()) {

            boolean notFound = tools.getDirVisitor().visitWhileAll(x, y,
                    next -> !(next instanceof Air || next instanceof Fire));

            if (!notFound)
                world.setAndChange(x, y, factory.getFireFocus(flam));
        }
    }

    protected final boolean burnElement(int x, int y, Tools tools, World world) {
        if (isFireInfluenced)
            return tools.getFireTools()
                    .affectBurnable(x, y, fireInfluenced, getTemperature());

        return false;
    }

    protected void extinguish(int x, int y, World world) {
        world.setAndChange(x, y, element);
        if (!element.hasTemperature())
            world.setTemperature(x, y, World.DEFAULT_TEMPERATURE);
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return true;
    }

    @Override
    public boolean hasTemperature() {
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