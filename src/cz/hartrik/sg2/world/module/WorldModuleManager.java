package cz.hartrik.sg2.world.module;

import cz.hartrik.sg2.world.ElementArea;
import java.util.List;

/**
 * Rozhraní pro správce modulů.
 * 
 * @version 2014-12-02
 * @author Patrik Harag
 * @param <T> typ správce elementů
 */
public interface WorldModuleManager<T extends ElementArea> {
    
    public List<WorldModule<T>> getModules();
    
    public void addModule(WorldModule<T> module);
    
    public void removeModule(WorldModule<T> module);
    
    public void nextCycle();
    
    public void refresh();
    
}