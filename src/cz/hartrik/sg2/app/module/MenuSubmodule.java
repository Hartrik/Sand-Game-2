
package cz.hartrik.sg2.app.module;

import cz.hartrik.sg2.app.Application;
import javafx.scene.control.MenuItem;

/**
 * Rozhraní pro sub-modul modulu {@link MenuModule}.
 * Slouží k přidávání položek do menu.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public interface MenuSubmodule {

    /**
     * Vytvoří položky do menu.
     *
     * @param app rozhraní aplikace
     * @return položky do menu
     */
    MenuItem[] createMenuItems(Application app);

}