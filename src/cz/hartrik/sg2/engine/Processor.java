
package cz.hartrik.sg2.engine;

import cz.hartrik.sg2.world.World;

/**
 * Rozhraní pro procesor - třída, která v určitém pořadí prochází elementy. 
 * 
 * @version 2014-05-01
 * @author Patrik Harag
 */
public interface Processor {
    
    /**
     * Provede další cyklus.
     */
    public void nextCycle();
    
    /**
     * Vrátí používanou instanci typu {@link World}.
     * 
     * @return world
     */
    public World getWorld();
    
    /**
     * Vrátí počet prošlích/aktivních chunků v předešlém cyklu.
     * 
     * @return počet prošlích chunků v předešlém cyklu
     */
    public int getLastUpdatedChunks();
    
}