
package cz.hartrik.sg2.world.element.flora;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.FallingElement;
import cz.hartrik.sg2.world.element.powder.Soil;
import cz.hartrik.sg2.world.element.Organic;

/**
 * Element představující semínko. Slouží k setí rostlin.
 * 
 * @version 2015-02-13
 * @author Patrik Harag
 */
public class Seed extends FallingElement implements Organic {
    
    private static final long serialVersionUID = 83715083867368_02_026L;

    private final Plant[] plants;
    private final Chance chance;
    
    /**
     * Vytvoří novou instanci.
     * 
     * @param color barva elementu
     * @param density hustota
     * @param chance šance na vyklíčení
     * @param plants rostliny, které mohou vyklíčit (bude náhodně vybráno)
     */
    public Seed(Color color, int density, Chance chance, Plant... plants) {
        super(color, density);
        this.chance = chance;
        this.plants = plants;
    }

    public final Plant[] getPlants() {
        return plants;
    }
    
    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        final int yUnder = y + 1;
        if (world.valid(x, yUnder)) {
            Element element = world.get(x, yUnder);
            
            if (element instanceof Air)
                tools.swap(x, y, this, x, yUnder, element);
            else if (element instanceof Soil && chance.nextBoolean())
                world.setAndChange(x, y, randomPlant(tools));
            else
                world.setAndChange(x, y, world.getBackground());
        } else
            world.setAndChange(x, y, world.getBackground());
    }

    private Plant randomPlant(Tools tools) {
        final int count = plants.length;
        return (count == 1)
                ? plants[0]
                : plants[tools.randomInt(count)];
    }
    
    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return true;
    }
    
}