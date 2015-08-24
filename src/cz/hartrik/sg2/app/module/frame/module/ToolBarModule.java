package cz.hartrik.sg2.app.module.frame.module;

import cz.hartrik.sg2.app.module.frame.StageModule;
import javafx.stage.Stage;

/**
 * @version 2014-09-23
 * @author Patrik Harag
 * @param <T>
 * @param <U>
 */
public abstract class ToolBarModule <T extends Stage, U extends IContainsToolBar>
        extends ModuleBase<T, U>
        implements StageModule<T, U> {

    public ToolBarModule(boolean register) {
        super(register);
    }

    @Override
    public void register(T stage, U controller, ServiceManager manager) {
        
    }
    
}