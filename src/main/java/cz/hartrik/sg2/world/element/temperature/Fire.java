package cz.hartrik.sg2.world.element.temperature;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.special.Sourceable;

/**
 * Element představující oheň. <p>
 *
 * V ohni se teplota šíří trochu jinak než normálně, proto má také svoji
 * vlastní teplotu. Jinak by nemohlo být dosaženo výsledného efektu.
 *
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class Fire extends FireElement implements Sourceable {

    private static final long serialVersionUID = 83715083867368_02_038L;

    public static final int MIN_TEMPERATURE = 400;

    private final int temperature;
    private final Color color;

    public Fire(Color color, int temperature) {
        this.temperature = temperature;
        this.color = color;
    }

    @Override public int getTemperature() { return temperature; }
    @Override public Color getColor() { return color; }
    @Override public int getDensity() { return -50; }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (tools.randomInt(3) != 0) return; // jinak by plam. mizely moc rychle

        int nTemperature = countTemperature(x, y, world, tools);
        if (nTemperature < MIN_TEMPERATURE) {
            // oheň zanikne
            world.setAndChange(x, y, world.getBackground());
            world.setTemperature(x, y, World.DEFAULT_TEMPERATURE);
            return;
        }

        final int up = y - 1;
        if (world.valid(x, up) && world.get(x, up) instanceof Air) {
            world.setAndChange(x, up,
                    tools.getFireTools().getFireFactory().getFire(nTemperature));
            world.setTemperature(x, up, nTemperature);
        } else {
            world.setAndChange(x, y,
                    tools.getFireTools().getFireFactory().getFire(nTemperature));
            world.setTemperature(x, y, nTemperature);
        }

        affectNearElements(x, y, tools, world);
    }

    protected int countTemperature(int x, int y, World world, Tools tools) {
        final int down = y + 1;
        int nTemperature =
                   getTemperatureAt(x + 1, down, world)
                 + getTemperatureAt(x - 1, down, world)
                 + getTemperatureAt(x,     down, world);
        nTemperature = (nTemperature + temperature) / 4;

        if (nTemperature < 900) // jinak by z toho byla pyramida
            if (tools.randomBoolean())
                nTemperature -= tools.randomInt(100);

        return nTemperature;
    }

    protected void affectNearElements(int x, int y, Tools tools, World world) {
        tools.getDirVisitor().visit(x, y,
                (Element element, int eX, int eY) -> {

            if (element instanceof Air) {
                // může se šířit vzduchem
                int fir = (int)(temperature * 0.7);
                if (fir > MIN_TEMPERATURE)
                    world.setAndChange(eX, eY, tools.getFireTools()
                            .getFireFactory().getFire(fir));

            } else if (element instanceof Fire) {
                // může oteplit nebo ochladit okolní oheň
                Fire fire = (Fire) element;
                if (fire.getTemperature() < temperature) {
                    int fir = (temperature - fire.getTemperature()) / 2;
                    if (fir > MIN_TEMPERATURE)
                        world.setAndChange(eX, eY, tools.getFireTools()
                                .getFireFactory().getFire(fir));
                }
            } else {
                // nebo ovlivní elemnty citlivé na oheň
                if (element instanceof FireAffected)
                    ((FireAffected) element).onFire(eX, eY, tools, world, temperature);
            }
        }, Direction.UP_LEFT, Direction.UP_RIGHT,  // # #    > grafické
           Direction.LEFT, Direction.RIGHT,        // #O#      vyobrazení
           Direction.DOWN);                        //  #
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return true;
    }

    @Override
    public boolean hasTemperature() {
        return true;
    }

}