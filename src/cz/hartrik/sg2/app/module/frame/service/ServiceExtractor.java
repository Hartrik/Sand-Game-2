package cz.hartrik.sg2.app.module.frame.service;

import cz.hartrik.sg2.app.module.frame.Application;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Třída poskytující statické metody pro extrakci služeb z instancí označených
 * jako {@link ServiceProvider}.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
public class ServiceExtractor {

    private ServiceExtractor() {}

    /**
     * Extrahuje služby.
     *
     * @param provider poskytovatel služeb
     * @return slovník obsahující názvy služeb a spustitelné služby
     */
    public static Map<String, ExecutableService> extract(Object provider) {
        Map<String, ExecutableService> map = new LinkedHashMap<>();

        for (Method method : provider.getClass().getMethods()) {
            Service annotation = method.getAnnotation(Service.class);
            if (annotation == null)
                continue;  // nemá požadovanou anotaci

            String name = annotation.value();

            ExecutableService service = asService(provider, method);
            if (service != null)
                map.put(name, service);
        }

        return map;
    }

    // vytvoří spustitelnou službu
    private static ExecutableService asService(Object provider, Method method) {
        return (Application application) -> {
            try {
                method.invoke(provider, application);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

}