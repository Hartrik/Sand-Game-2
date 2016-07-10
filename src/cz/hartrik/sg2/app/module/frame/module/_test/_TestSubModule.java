package cz.hartrik.sg2.app.module.frame.module._test;

import cz.hartrik.sg2.app.module.frame.Application;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import javafx.scene.control.MenuItem;

/**
 *
 * @author Patrik Harag
 */
public class _TestSubModule implements MenuSubmodule {

    @Override
    public MenuItem[] createMenuItems(Application app) {

        MenuItem menuItem = new MenuItem("print services");
        menuItem.setOnAction(event -> {
            app.getServiceManager()
                    .getServices()
                    .entrySet()
                    .forEach(System.out::println);
        });

        return new MenuItem[] { menuItem };
    }

}