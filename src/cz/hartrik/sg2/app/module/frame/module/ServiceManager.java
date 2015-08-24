
package cz.hartrik.sg2.app.module.frame.module;

import cz.hartrik.common.reflect.TODO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 2015-04-06
 * @author Patrik Harag
 */
@TODO("Nedokončené")
public class ServiceManager {
    
    private final Map<String, Runnable> map = new HashMap<>();
    
    public void register(String name, Runnable procedure) {
        map.put(name, procedure);
    }
    
    public void run(String name) {
        map.get(name).run();
    }
    
    public List<String> getList() {
        return map.keySet().stream().sorted().collect(Collectors.toList());
    }
    
    public Runnable getProcedure(String name) {
        return map.get(name);
    }
    
}