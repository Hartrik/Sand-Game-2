package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.common.random.RatioChance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.temperature.BurnableDef;
import cz.hartrik.sg2.world.element.temperature.FireSettings;

/**
 * Element představující napalm.
 * Zde se jedná o tekutinu, která vede teplo, snadno se vznítí a teplota
 * plamenů dosahuje 2100 °C. Dokud element padá, tak nedochází k jeho spalování.
 * <p>
 *
 * <i>Může být vytvořena pouze jedna instance tohoto elementu pro použití
 * na celém plátně. Sice bude všude stejná teplota, ale to stejně není na
 * první pohled poznat.</i>
 *
 * @version 2015-11-18
 * @author Patrik Harag
 */
public class Napalm extends FluidWaterTC implements BurnableDef {

    private static final long serialVersionUID = 83715083867368_02_093L;

    private static final FireSettings fs = new FireSettings(
            Chance.ALWAYS, 100, 2100, new RatioChance(20));

    public Napalm(Color color, int density, int temperature) {
        super(color, density, temperature);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        super.doAction(x, y, tools, world);

        // spálení elementu
        if (getFireSettings().getChanceToBurn().nextBoolean())
            world.setAndChange(x, y, world.getBackground());
    }

    @Override
    public float getConductiveIndex() {
        return .5f;
    }

    @Override
    public FireSettings getFireSettings() {
        return fs;
    }

    @Override
    public void setTemperature(int temperature) {
        // teplotu nelze snížit
        if (temperature > getTemperature())
            super.setTemperature(temperature);
    }

}