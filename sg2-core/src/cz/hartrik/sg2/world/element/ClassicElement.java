
package cz.hartrik.sg2.world.element;

import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import java.io.Serializable;

/**
 * Abstraktní třída pro všechny klasické elementy.
 * 
 * @version 2014-09-04
 * @author Patrik Harag
 */
public abstract class ClassicElement implements Element, Serializable {
    
    private static final long serialVersionUID = 83715083867368_02_000L;

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return false;
    }
    
}