
package cz.hartrik.sg2.app.module.frame.module;

import cz.hartrik.sg2.app.module.frame.Application;
import java.util.ArrayList;
import java.util.Collection;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 * Modul přidávající menu. Položky do menu jsou přidávány prostřednictvím
 * sub-modulů. Mezi položky z různých sub-modulů jsou umístěny oddělovače.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class MenuModule implements ApplicationModule, ApplicationCompoundModule {

    protected final String text;
    protected final Collection<MenuSubmodule> submodules;

    public MenuModule(String text) {
        this(text, new ArrayList<>());
    }

    public MenuModule(String text, Collection<MenuSubmodule> submodules) {
        this.text = text;
        this.submodules = submodules;
    }

    public MenuModule add(MenuSubmodule submodule) {
        submodules.add(submodule);
        return this;
    }

    @Override
    public void init(Application application) {

        Menu menu = new Menu(text);
        ObservableList<MenuItem> items = menu.getItems();

        int counter = 0;
        for (MenuSubmodule mod : submodules) {
            items.addAll(mod.createMenuItems(application));

            if (counter++ < submodules.size() - 1)
                items.add(new SeparatorMenuItem());
        }

        application.getController().getMenuBar().getMenus().add(menu);
    }

    @Override
    public Object[] getSubModules() {
        return submodules.toArray();
    }

}