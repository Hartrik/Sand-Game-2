
package cz.hartrik.sg2.app.module;

import cz.hartrik.sg2.app.Application;

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
    void init(Application app);

}