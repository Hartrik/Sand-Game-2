
package cz.hartrik.sg2.app.module.edit;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.app.service.Require;
import cz.hartrik.sg2.app.service.ServiceManager;
import javafx.scene.control.MenuItem;

/**
 * Sub-modul pro testování výkonu aplikace.
 *
 * @version 2017-08-06
 * @author Patrik Harag
 */
@Require(PerformanceTestServices.class)
public class PerformanceTestSubmodule implements MenuSubmodule {

    @Override
    public MenuItem[] createMenuItems(Application app) {
        ServiceManager manager = app.getServiceManager();

        MenuItem iSandTest = new MenuItem(Strings.get("module.edit.benchmark-1"));
        iSandTest.setOnAction((e) -> {
            manager.run(PerformanceTestServices.SERVICE_TEST_BENCHMARK_1);
        });

        MenuItem iBenchmark1 = new MenuItem(Strings.get("module.edit.benchmark-2"));
        iBenchmark1.setOnAction((e) -> {
            manager.run(PerformanceTestServices.SERVICE_TEST_BENCHMARK_2);
        });

        MenuItem iBenchmark2 = new MenuItem(Strings.get("module.edit.benchmark-3"));
        iBenchmark2.setOnAction((e) -> {
            manager.run(PerformanceTestServices.SERVICE_TEST_BENCHMARK_3);
        });

        return new MenuItem[] { iSandTest, /*iBenchmark1, iBenchmark2*/ };
    }

}