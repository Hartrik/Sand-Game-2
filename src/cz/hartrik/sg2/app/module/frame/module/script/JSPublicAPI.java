package cz.hartrik.sg2.app.module.frame.module.script;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.module.frame.FrameController;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @version 2015-03-27
 * @author Patrik Harag
 */
public class JSPublicAPI {
    
    public static Map<String, Supplier<?>> createBindings(FrameController controller) {

        final Map<String, Supplier<?>> map = new HashMap<>();
        map.put("canvas", () -> new Canvas(controller.getWorld(),
                                           controller.getControls()));

        map.put("serviceManager", controller::getServiceManager);
        map.put("brushManager", controller::getBrushManager);
        map.put("controls", controller::getControls);

        // since 2.02
        map.put("ToolFactory", ToolFactory::getInstance);
        map.put("Turtle", () -> new TurtleFactory(controller.getWorld(),
                                                  controller.getControls()));
        return map;
    }
    
    public static String loadInitCode() {
        return Resources.text(JSPublicAPI.class.getResourceAsStream("init.js"));
    }
    
    public static String defaultCode() {
        return "// Bindings:\n"
             + "//   canvas, brushManager, serviceManager, controls" + "\n\n";
    }
    
}