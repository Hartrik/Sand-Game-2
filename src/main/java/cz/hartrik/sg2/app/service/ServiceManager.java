
package cz.hartrik.sg2.app.service;

import cz.hartrik.sg2.app.Application;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Spravuje a spouští všechny služby.
 *
 * @version 2017-07-11
 * @author Patrik Harag
 */
public class ServiceManager {

    private static final Logger LOGGER = Logger.getLogger(ServiceManager.class.getName());

    private final Map<String, ExecutableService> services = new LinkedHashMap<>();
    private final Application app;

    public ServiceManager(Application app) {
        this.app = app;
    }

    // ----

    public void register(Map<String, ExecutableService> map) {
        map.entrySet().forEach((entry) -> {
            String key = entry.getKey();

            if (services.containsKey(key))
                throw new IllegalArgumentException(
                        "key '" + key + "' is already present");
            else
                services.put(key, entry.getValue());

            LOGGER.info("register: " + key);
        });
    }

    public void register(String name, Runnable procedure) {
        services.put(name, (a) -> procedure.run());
    }

    public void register(Object provider) {
        register(ServiceExtractor.extract(provider));
    }

    // ----

    public void run(String name) {
        if (services.containsKey(name))
            services.get(name).accept(app);
        else
            throw new IllegalArgumentException("service '" + name + "' not found");

        LOGGER.info("run: " + name);
    }

    // ---

    public Map<String, ExecutableService> getServices() {
        return Collections.unmodifiableMap(services);
    }

}