package cz.hartrik.sg2.app.module.options;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.app.service.Require;
import cz.hartrik.sg2.app.service.ServiceManager;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;

/**
 * Sub-modul do menu, který přidá možnost nechat propadávat elementy dnem
 * plátna.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@Require(WorldModuleServices.class)
public class NoBottomSubmodule implements MenuSubmodule {

    @Override
    public MenuItem[] createMenuItems(Application app) {

        CheckMenuItem item = new CheckMenuItem("Propadávání elementů dnem plátna");
        item.selectedProperty().addListener((ob, old, selected) -> {
            ServiceManager manager = app.getServiceManager();

            if (selected)
                manager.run(WorldModuleServices.SERVICE_MODULE_NO_BOTTOM_ON);
            else
                manager.run(WorldModuleServices.SERVICE_MODULE_NO_BOTTOM_OFF);
        });

        return new MenuItem[] { item };
    }

}