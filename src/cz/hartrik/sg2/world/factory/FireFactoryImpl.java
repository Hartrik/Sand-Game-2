
package cz.hartrik.sg2.world.factory;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.element.NonSolidElement;
import cz.hartrik.sg2.world.element.temperature.Fire;
import cz.hartrik.sg2.world.element.temperature.FireFocus;
import cz.hartrik.sg2.world.element.temperature.FireFocusMovable;
import cz.hartrik.sg2.world.element.temperature.Flammable;

/**
 * Implementace "továrny na oheň".
 * 
 * @version 2014-05-15
 * @author Patrik Harag
 */
public class FireFactoryImpl implements IFireFactory {
    
    private static final long serialVersionUID = 83715083867368_10_007L;
    
    private static final Color c3000 = new Color(249, 238, 58);
    private static final Color c2500 = new Color(249, 219, 30);
    private static final Color c2000 = new Color(248, 201,  7);
    private static final Color c1500 = new Color(250, 150,  3);
    private static final Color c1000 = new Color(255, 111,  0);
    private static final Color c950  = new Color(255,  37,  0);
    private static final Color c900  = new Color(250,   4,  5);
    private static final Color dark  = new Color(125,   0,  0);

    private final MapFactory<Integer, Fire> fireFact
            = new MapFactory<Integer, Fire>() {
                
        private static final long serialVersionUID = 83715083867368_03_001L;
        
        private Color getColor(int temperature) {
            if (temperature > 3000) return c3000;
            if (temperature > 2500) return c2500;
            if (temperature > 2000) return c2000;
            if (temperature > 1500) return c1500;
            if (temperature > 1000) return c1000;
            if (temperature > 950)  return c950;
            if (temperature > 900)  return c900;
                                    return dark;
        }
        
        @Override
        protected Fire createInstance(Integer temperature) {
            return new Fire(getColor(temperature), temperature);
        }
    };
    
    @Override
    public Fire getFire(Integer temperature) {
        return fireFact.apply(temperature);
    }

    @Override
    public FireFocus getFireFocus(Flammable flammable) {
        return flammable instanceof NonSolidElement
                ? new FireFocusMovable(flammable, this)
                : new FireFocus(flammable, this);
    }
    
}