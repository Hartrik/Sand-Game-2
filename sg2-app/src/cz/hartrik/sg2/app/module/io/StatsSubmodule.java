package cz.hartrik.sg2.app.module.io;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.app.service.Require;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * Sub-modul do menu přidávající položku pro zobrazení okna se statistikami.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@Require(StatsService.class)
public class StatsSubmodule implements MenuSubmodule {

    @Override
    public MenuItem[] createMenuItems(Application app) {

        MenuItem iStats = new MenuItem("Zobrazit statistiky");
        iStats.setAccelerator(KeyCombination.valueOf("ctrl+t"));
        iStats.setOnAction((e) -> {
            app.getServiceManager().run(StatsService.SERVICE_STATS);
        });

        return new MenuItem[] { iStats };
    }

}