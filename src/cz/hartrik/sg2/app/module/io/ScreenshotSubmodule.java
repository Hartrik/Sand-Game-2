
package cz.hartrik.sg2.app.module.io;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.app.service.Require;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * Sub-modul do menu, který přidá položku pro vytvoření screenshotu.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@Require(ScreenshotService.class)
public class ScreenshotSubmodule implements MenuSubmodule {

    @Override
    public MenuItem[] createMenuItems(Application app) {

        MenuItem item = new MenuItem("Rychlý screenshot");
        item.setAccelerator(KeyCombination.valueOf("ctrl+p"));
        item.setOnAction(e -> {
            app.getServiceManager().run(ScreenshotService.SERVICE_SCREENSHOT);
        });

        return new MenuItem[] { item };
    }

}