package cz.hartrik.sg2.world.module;

import cz.hartrik.sg2.world.ElementArea;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @version 2015-03-07
 * @author Patrik Harag
 * @param <T> typ správce elementů
 */
public class BasicWorldModuleManager<T extends ElementArea>
        implements WorldModuleManager<T> {

    public BasicWorldModuleManager(T area) {
        this.area = area;
    }
    
    private final T area;
    private final List<WorldModule<T>> modules = new LinkedList<>();

    @Override
    public List<WorldModule<T>> getModules() {
        return modules;
    }

    @Override
    public synchronized void addModule(WorldModule<T> module) {
        modules.add(module);
    }
    
    @Override
    public synchronized void removeModule(WorldModule<T> module) {
        modules.remove(module);
    }
    
    @Override
    public synchronized void nextCycle() {
        Iterator<WorldModule<T>> iterator = modules.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().nextCycle(area))
                iterator.remove();
        }
    }

    @Override
    public synchronized void refresh() {
        Iterator<WorldModule<T>> iterator = modules.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().refresh(area))
                iterator.remove();
        }
    }

}