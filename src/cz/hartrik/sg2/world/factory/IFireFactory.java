
package cz.hartrik.sg2.world.factory;

import cz.hartrik.sg2.world.element.temperature.Fire;
import cz.hartrik.sg2.world.element.temperature.FireFocus;
import cz.hartrik.sg2.world.element.temperature.Flammable;
import java.io.Serializable;

/**
 * Rozhraní pro "továrnu" na oheň.
 * 
 * @version 2014-12-28
 * @author Patrik Harag
 */
public interface IFireFactory extends Serializable {
    
    public Fire      getFire(Integer temperature);
    public FireFocus getFireFocus(Flammable flammable);
    
}