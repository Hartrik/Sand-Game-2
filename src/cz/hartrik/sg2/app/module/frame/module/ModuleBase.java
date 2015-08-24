package cz.hartrik.sg2.app.module.frame.module;

import cz.hartrik.sg2.app.module.frame.StageModule;

/**
 * @version 2014-09-23
 * @author Patrik Harag
 * @param <T>
 * @param <U>
 */
public abstract class ModuleBase<T, U> implements StageModule<T, U> {

    private final boolean register;

    public ModuleBase(boolean register) {
        this.register = register;
    }
    
    @Override
    public final void init(T stage, U controller, ServiceManager manager) {
        if (register) {
            register(stage, controller, manager);
        }
        
        setUp(stage, controller, manager);
    }
    
    public abstract void register(T stage, U controller, ServiceManager manager);
    
    public abstract void setUp(T stage, U controller, ServiceManager manager);
    
}