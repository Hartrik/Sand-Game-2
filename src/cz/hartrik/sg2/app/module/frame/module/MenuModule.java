
package cz.hartrik.sg2.app.module.frame.module;

import cz.hartrik.sg2.app.module.frame.StageModule;
import java.util.ArrayList;
import java.util.Collection;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.Stage;

/**
 * @version 2014-12-02
 * @author Patrik Harag
 * @param <T>
 * @param <U>
 * 
 */
public class MenuModule <T extends Stage, U extends IContainsMenu> 
        implements StageModule<T, U> {
    
    protected final String text;
    protected final Collection<MenuSubmodule<T, U>> submodules;
    
    public MenuModule(String text) {
        this(text, new ArrayList<>());
    }
    
    public MenuModule(String text, Collection<MenuSubmodule<T, U>> submodules) {
        this.text = text;
        this.submodules = submodules;
    }
    
    public MenuModule<T, U> add(MenuSubmodule<T, U> submodule) {
        submodules.add(submodule);
        return this;
    }
    
    @Override
    public void init(T stage, U controller, ServiceManager manager) {
        
        Menu menu = new Menu(text);
        ObservableList<MenuItem> items = menu.getItems();
        
        int counter = 0;
        for (MenuSubmodule<T, U> mod : submodules) {
            items.addAll(mod.init(stage, controller, manager));
            
            if (counter++ < submodules.size() - 1)
                items.add(new SeparatorMenuItem());
        }
        
        controller.getMenuBar().getMenus().add(menu);
    }
    
}