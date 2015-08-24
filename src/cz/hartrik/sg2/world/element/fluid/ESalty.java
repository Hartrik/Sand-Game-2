package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.sg2.world.Element;

/**
 * @version 2014-12-29
 * @author Patrik Harag
 */
public interface ESalty extends Element {
    
    public void setSalt(int salt);

    public int getSalt();
    
}