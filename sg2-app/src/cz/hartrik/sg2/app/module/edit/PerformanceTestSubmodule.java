
package cz.hartrik.sg2.app.module.edit;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.app.service.Require;
import cz.hartrik.sg2.app.service.ServiceManager;
import javafx.scene.control.MenuItem;

/**
 * Sub-modul pro testování výkonu aplikace.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@Require(PerformanceTestServices.class)
public class PerformanceTestSubmodule implements MenuSubmodule {

    @Override
    public MenuItem[] createMenuItems(Application app) {
        ServiceManager manager = app.getServiceManager();

        MenuItem iSandTest = new MenuItem("Test výkonu - písek");
        iSandTest.setOnAction((e) -> {
            manager.run(PerformanceTestServices.SERVICE_TEST_SAND_FALL);
        });

        MenuItem iBenchmark = new MenuItem("Test výkonu - benchmark");
        iBenchmark.setOnAction((e) -> {
            manager.run(PerformanceTestServices.SERVICE_TEST_BENCHMARK);
        });

        return new MenuItem[] { iSandTest, /*iBenchmark*/ };
    }

}