package cz.hartrik.sg2.app.module.script;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.Application;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @version 2017-07-05
 * @author Patrik Harag
 */
public class JSPublicAPI {

    private final Application app;

    private Map<String, Supplier<?>> bindings;

    public JSPublicAPI(Application app) {
        this.app = app;
    }

    public synchronized Map<String, Supplier<?>> getBindings() {
        if (bindings == null) {
            bindings = createBindings();
        }
        return createBindings();
    }

    private Map<String, Supplier<?>> createBindings() {
        Map<String, Supplier<?>> map = new HashMap<>();
        map.put("canvas", () -> new Canvas(app.getWorld(), app.getControls()));

        map.put("serviceManager", app::getServiceManager);
        map.put("brushManager", app::getBrushManager);
        map.put("controls", app::getControls);

        // since 2.02
        map.put("ToolFactory", ToolFactory::getInstance);
        map.put("Turtle", () -> new TurtleFactory(app.getWorld(), app.getControls()));

        return map;
    }

    public String loadInitCode() {
        return Resources.text(JSPublicAPI.class.getResourceAsStream("init.js"));
    }

    public String defaultCode() {
        Map<String, Supplier<?>> bindings = getBindings();

        String globals = bindings.keySet().stream()
                .filter(key -> Character.isLowerCase(key.charAt(0)))
                .sorted()
                .collect(Collectors.joining(", "));

        String classes = bindings.keySet().stream()
                .filter(key -> Character.isUpperCase(key.charAt(0)))
                .sorted()
                .collect(Collectors.joining(", "));

        return "// Globals:\n"
             + "//   " + globals + "\n"
             + "// Classes:\n"
             + "//   " + classes + "\n\n";
    }

}