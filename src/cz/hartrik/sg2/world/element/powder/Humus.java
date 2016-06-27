
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.random.Chance;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;

/**
 * Element představující humus.
 * Humus je soubor odumřelých organických látek rostlinného i živočišného
 * původu. Mění se na půdu. http://en.wikipedia.org/wiki/Humus
 * 
 * @version 2014-03-11
 * @author Patrik Harag
 */
public class Humus extends Soil {

    private static final long serialVersionUID = 83715083867368_02_018L;

    private final Element[] soil;
    private final Chance chance;

    public Humus(Color color, int density, Chance chance, Element... soil) {
        super(color, density);
        this.chance = chance;
        this.soil = soil;
    }

    public final Element[] getSoil() {
        return soil;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (super.testAction(x, y, tools, world)) {
            super.doAction(x, y, tools, world);
        } else {
            if (chance.nextBoolean()) {
                world.set(x, y, soil[tools.randomInt(soil.length)]);
                world.getChunkAt(x, y).change();
            }
        }
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return true; // dokud se nepřemnění na hlínu
    }

}