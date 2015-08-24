
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.temperature.BurnableDef;
import cz.hartrik.sg2.world.element.temperature.FireSettings;
import cz.hartrik.sg2.world.element.temperature.ThermalInfluencedDef;

/**
 * Abstraktní třída pro elementy pískového typu, které šíří teplo, hoří a
 * spalují se.
 * 
 * @version 2014-05-22
 * @author Patrik Harag
 */
public abstract class PowderMidTCF extends PowderMidTC
        implements ThermalInfluencedDef, BurnableDef {
    
    private static final long serialVersionUID = 83715083867368_02_062L;
    
    private final FireSettings fireSettings;

    public PowderMidTCF(Color color, int density, FireSettings fireSettings) {
        this(color, density, fireSettings, NORMAL_TEMP);
    }

    public PowderMidTCF(Color color, int density, FireSettings fireSettings,
            int temperature) {
        
        super(color, density, temperature);
        this.fireSettings = fireSettings;
    }

    // BurnableDef
    
    @Override
    public FireSettings getFireSettings() {
        return fireSettings;
    }
    
    // ThermalInfluenced
    
    @Override
    public boolean temperature(int x, int y, Tools tools, World world,
            int degrees, boolean fire) {
        
        // převzetí tepla
        super.temperature(x, y, tools, world, degrees, fire);
        
        // spalování
        return ThermalInfluencedDef.super
                .temperature(x, y, tools, world, degrees, fire);
    }
    
}