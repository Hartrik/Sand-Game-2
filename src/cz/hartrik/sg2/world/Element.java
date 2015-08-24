
package cz.hartrik.sg2.world;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.process.Tools;


/**
 * Rozhraní pro element.
 * 
 * @version 2015-02-15
 * @author Patrik Harag
 */
public interface Element {
    
    /**
     * Vrátí barvu elementu.
     * 
     * @return barva
     */
    public Color getColor();
    
    /**
     * Hustota elementů.
     * Jeden element se dostane pod druhý, jestliže rozdíl jejich hodnot
     * (<code>e1 - e2</code>) bude vyšší než 0. Při rozdílu &gt 0 se s každým
     * dalším číslem zvyšuje šance o 1 %.
     * 
     * @return hustota
     */
    public int getDensity();
    
    /**
     * V metodě je implementováno specifické chování elementu.
     * 
     * @param x horizontální pozice v poli
     * @param y vertikální pozice v poli
     * @param tools pomocné nástroje
     * @param world "svět", na kterém se element nachází
     */
    public void doAction(int x, int y, Tools tools, World world);
    
    public default void doParallel(int x, int y, Tools tools, World world) {}
    
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
    
}