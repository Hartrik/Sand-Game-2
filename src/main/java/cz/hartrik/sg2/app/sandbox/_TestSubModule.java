package cz.hartrik.sg2.app.sandbox;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.MenuSubmodule;
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