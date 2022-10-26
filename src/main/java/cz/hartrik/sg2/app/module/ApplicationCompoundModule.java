package cz.hartrik.sg2.app.module;

/**
 * Rozhraní pro modul, který se skládá z dalších sub-modulů. <p>
 *
 * Důvodem existence tohoto rozhraní je, že i sub-moduly mohou vyžadovat nějaké
 * služby (používat anotaci {@link cz.hartrik.sg2.app.service.Require})
 * nezávisle na nadřazeném modulu, nebo sami služby poskytovat.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public interface ApplicationCompoundModule {

    /**
     * Vrátí seznam všech sub-modulů. Typ těchto sub-modulů není podstatný,
     * podstatné jsou pouze jejich rozhraní a anotace.
     *
     * @see cz.hartrik.sg2.app.service.Require
     * @see cz.hartrik.sg2.app.service.Service
     * @see cz.hartrik.sg2.app.service.ServiceProvider
     *
     * @return pole sub-modulů
     */
    Object[] getSubModules();

}