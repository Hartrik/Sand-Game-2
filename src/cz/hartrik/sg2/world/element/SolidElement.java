
package cz.hartrik.sg2.world.element;

/**
 * Abstraktní třída pro pevné/statické elementy.
 * 
 * @version 2014-03-09
 * @author Patrik Harag
 */
public abstract class SolidElement extends ClassicElement {
    
    private static final long serialVersionUID = 83715083867368_02_028L;

    @Override
    public int getDensity() {
        return Integer.MAX_VALUE / 2;
    }
    
}