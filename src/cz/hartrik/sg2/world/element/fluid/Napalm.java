package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.random.Chance;
import cz.hartrik.sg2.random.RatioChance;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.temperature.BurnableDef;
import cz.hartrik.sg2.world.element.temperature.FireSettings;

/**
 * Element představující napalm.
 * Zde se jedná o tekutinu, který se ihned vznítí a teplota
 * plamenů dosahuje 2350 °C. Dokud element padá, tak nedochází k jeho spalování.
 * <p>
 *
 * <i> Může být vytvořena pouze jedna instance tohoto elementu pro použití
 * na celém plátně. </i>
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public class Napalm extends FluidWater implements BurnableDef {

    private static final long serialVersionUID = 83715083867368_02_093L;

    private static final FireSettings fs = new FireSettings(
            Chance.ALWAYS, 0, 2350, new RatioChance(20));

    public Napalm(Color color, int density) {
        super(color, density);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        world.setTemperature(x, y, getFlammableNumber());

        if (!flow(x, y, tools, world)) {
            tools.getFireTools().affectFlammable(x, y, this, false);
        }
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return true;
    }

    @Override
    public boolean hasTemperature() {
        return true;
    }

    @Override
    public boolean isConductive() {
        return true;
    }

    @Override
    public float getConductiveIndex() {
        return 0.5f;
    }

    @Override
    public float loss() {
        return 0;
    }

    // BurnableDef

    @Override
    public FireSettings getFireSettings() {
        return fs;
    }

}