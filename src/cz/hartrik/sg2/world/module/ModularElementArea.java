package cz.hartrik.sg2.world.module;

/**
 * Rozhraní pro třídu spravující pole elementů, které podporuje moduly.
 * 
 * @version 2014-11-25
 * @author Patrik Harag
 */
public interface ModularElementArea {
    
    /**
     * Vrací správce modulů.
     * 
     * @return správce modulů
     */
    public WorldModuleManager<?> getModuleManager();
    
}