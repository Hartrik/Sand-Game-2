
package cz.hartrik.sg2.world.element.type;

import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.special.DesiccatingPowder;
import cz.hartrik.sg2.world.element.special.Sponge;

/**
 * Rozhraní pro elementy, které mohou být vysušeny. Rozhraní je spojené např. s
 * chováním elementů {@link DesiccatingPowder} a {@link Sponge}.
 * 
 * @version 2014-05-01
 * @author Patrik Harag
 */
public interface Dryable extends Element {
    
    public boolean dry(int x, int y, Tools tools, World world);
    
}