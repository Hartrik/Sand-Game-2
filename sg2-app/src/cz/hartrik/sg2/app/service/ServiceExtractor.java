package cz.hartrik.sg2.app.service;

import cz.hartrik.sg2.app.Application;
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
class ServiceExtractor {

    private ServiceExtractor() {}

    /**
     * Extrahuje služby.
     *
     * @param provider poskytovatel služeb
     * @return slovník obsahující názvy služeb a spustitelné služby
     */
    static Map<String, ExecutableService> extract(Object provider) {
        Map<String, ExecutableService> map = new LinkedHashMap<>();

        for (Method method : provider.getClass().getMethods()) {
            Service annotation = method.getAnnotation(Service.class);
            if (annotation == null)
                continue;  // nemá požadovanou anotaci

            String name = annotation.value();

            ExecutableService service = asService(provider, method);
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