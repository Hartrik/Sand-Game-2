
package cz.hartrik.sg2.app.module.frame.module;

import cz.hartrik.sg2.app.module.frame.Application;

/**
 * Rozhraní pro modul, který do aplikace přidá nějakou funkcionalitu.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public interface ApplicationModule {

    /**
     * Přidá modul do aplikace.
     *
     * @param app rozhraní aplikace
     */
    public void init(Application app);

}