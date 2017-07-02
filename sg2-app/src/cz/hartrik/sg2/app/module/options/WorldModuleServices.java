package cz.hartrik.sg2.app.module.options;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.service.Service;
import cz.hartrik.sg2.app.service.ServiceProvider;
import cz.hartrik.sg2.world.module.ModuleNoBottom;

/**
 * Poskytuje služby pro zapínání a vypínání modulů
 * {@link cz.hartrik.sg2.world.ModularWorld}.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@ServiceProvider
public class WorldModuleServices {

    public static final String SERVICE_MODULE_NO_BOTTOM_ON = "no-bottom-on";
    public static final String SERVICE_MODULE_NO_BOTTOM_OFF = "no-bottom-off";

    private final ModuleNoBottom noBottomModule = new ModuleNoBottom();

    // služby

    @Service(SERVICE_MODULE_NO_BOTTOM_ON)
    public void noBottomON(Application app) {
        app.getController().removeWorldModule(noBottomModule);
        app.getController().addWorldModule(noBottomModule);
    }

    @Service(SERVICE_MODULE_NO_BOTTOM_OFF)
    public void noBottomOFF(Application app) {
        app.getController().removeWorldModule(noBottomModule);
    }

}