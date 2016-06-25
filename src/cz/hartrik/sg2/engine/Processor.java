
package cz.hartrik.sg2.engine;

import cz.hartrik.sg2.process.Tools;

/**
 * Rozhraní pro procesor - třída, která v určitém pořadí prochází elementy.
 *
 * @version 2016-06-25
 * @author Patrik Harag
 */
public interface Processor {

    /**
     * Provede další cyklus.
     */
    public void nextCycle();

    /**
     * Vrátí počet prošlých/aktivních chunků v předešlém cyklu.
     *
     * @return počet prošlých chunků v předešlém cyklu
     */
    public int getLastUpdatedChunks();

    /**
     * Poskytne nástroje používané elementy pro implementaci jejich chování.
     *
     * @return Tools
     */
    public Tools getTools();

    /**
     * Tato metoda je volána, když už není tento procesor potřeba.
     * Slouží především k uvolnění zdrojů.
     */
    public default void shutdown() { }

}