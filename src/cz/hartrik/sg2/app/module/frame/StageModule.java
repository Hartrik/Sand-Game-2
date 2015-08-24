
package cz.hartrik.sg2.app.module.frame;

import cz.hartrik.sg2.app.module.frame.module.ServiceManager;

/**
 * @version 2014-09-21
 * @author Patrik Harag
 * @param <T>
 * @param <U>
 */
public interface StageModule<T, U> {
    
    public void init(T stage, U controller, ServiceManager manager);
    
}