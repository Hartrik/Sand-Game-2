
package cz.hartrik.sg2.app.module.frame.module;

import javafx.scene.control.MenuItem;

/**
 * @version 2014-09-23
 * @author Patrik Harag
 * @param <T> stage
 * @param <U> controller
 */
public abstract class MenuSubmodule<T, U> {
    
    protected final boolean register;

    public MenuSubmodule(boolean register) {
        this.register = register;
    }
    
    public MenuItem[] init(T stage, U controller, ServiceManager manager) {
        if (register)
            register(stage, controller, manager);
        
        return createMenuItems(stage, controller, manager);
    }
    
    public abstract MenuItem[] createMenuItems(T stage, U controller, ServiceManager manager);
    
    public void register(T stage, U controller, ServiceManager manager) {
        // mnohé ze submodulů nepotřebují registrovat služby
    }
    
}