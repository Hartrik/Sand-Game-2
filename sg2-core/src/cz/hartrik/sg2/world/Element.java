
package cz.hartrik.sg2.world;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;

/**
 * Rozhraní pro element.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public interface Element {

    /**
     * Vrátí barvu elementu.
     * Barva nemusí být statická, při každém zavolání může klidně vrátit jinou.
     *
     * @return barva elementu
     */
    public Color getColor();

    /**
     * Vrátí barvu elementu.
     * Teplota předávaná parametrem může například určit jeho zabarvení. <p>
     * Výchozí implementace volá metodu {@link #getColor() getColor()}.
     *
     * @param temperature aktuální teplota
     * @return barva elementu
     */
    public default Color getColor(float temperature) {
        return getColor();
    }

    /**
     * V metodě je implementováno specifické chování elementu.
     *
     * @param x horizontální pozice v poli
     * @param y vertikální pozice v poli
     * @param tools pomocné nástroje
     * @param world "svět", na kterém se element nachází
     */
    public void doAction(int x, int y, Tools tools, World world);

    /**
     * Každý element musí mít implementovanou tuto metodu, pomoci níž se
     * testuje, zda je možné chunk, ve kterém se element nachází "uspat".
     *
     * @param x horizontální pozice v poli
     * @param y vertikální pozice v poli
     * @param tools pomocné nástroje
     * @param world "svět", na kterém se element nachází
     * @return <code>true</code>  - element se má kam hýbat / co dělat <br>
     *         <code>false</code> - element nemůže nic dalšího dělat a chunk
     *                              tedy případně může hibernovat
     */
    public boolean testAction(int x, int y, Tools tools, World world);


    //
    // Následující metody by mohly být definovány až v nějakém dalším rozhraní
    // implementující Element, původně i byly.
    // Zde jsou umístěny čistě kvůli rychlosti.
    //


    /**
     * Hustota elementu. <p>
     *
     * Jeden element se dostane pod druhý, jestliže rozdíl jejich hodnot
     * (<code>e1 - e2</code>) bude vyšší než 0. Při rozdílu &gt 0 se s každým
     * dalším číslem zvyšuje šance o 1 %. <p>
     *
     * Hustota má smysl u nestatických elementů.
     *
     * @return hustota
     */
    public int getDensity();

    /**
     * Vrátí {@code true}, pokud element může nést teplotu.
     *
     * @return boolean
     */
    public default boolean hasTemperature() {
        return false;
    }

    /**
     * Vrátí {@code true}, pokud element aktivně "rozvádí" teplotu mezi elementy
     * ve svém okolí. <p>
     *
     * Pozor:
     * <i> pokud tato metoda vrací {@code false}, jiné elementy mohou teplotu
     * tohoto elementu stejně ovlivnit. Teplota tedy nebude statická. </i>
     *
     * @return boolean
     */
    public default boolean isConductive() {
        return false;
    }

    /**
     * Udává poměrné množství tepla, které tento element může od ostatních
     * elementů v jeho okolí za jeden cyklus odvést. <p>
     *
     * Číslo v rozmezí 0 - 0.5,
     * při indexu 0 neodvede z jiných elementů žádné teplo,
     * při indexu 0.5 odvede až polovinu tepla (teplo se na ploše zaplněné
     * těmito elementy rovnoměrně rozprostře).
     *
     * @return index
     */
    public default float getConductiveIndex() {
        return .5f;
    }

    /**
     * Ztráta teploty při každém cyklu.
     *
     * @return ztráta teploty
     */
    public default float loss() {
        return 1;
    }

}