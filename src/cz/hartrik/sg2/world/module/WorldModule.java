package cz.hartrik.sg2.world.module;

import cz.hartrik.sg2.world.ElementArea;

/**
 * Rozhraní pro modul.
 * 
 * @version 2015-03-07
 * @author Patrik Harag
 * @param <T> typ
 */
public interface WorldModule<T extends ElementArea> {

    /**
     * Metoda je volána na začátku každého cyklu.
     * Pokud se má nyní provádět nějaká činnost, volá metodu
     * {@link #refresh(cz.hartrik.sg2.world.ElementArea) refresh(T)}.
     * 
     * @param area pole elementů
     * @return pokud vrtátí <code>false</code>, bude tento modul odstraněn
     */
    public boolean nextCycle(T area);

    /**
     * Provede činnost modulu okamžitě.
     * 
     * @param area pole elementů
     * @return pokud vrtátí <code>false</code>, bude tento modul odstraněn
     */
    public boolean refresh(T area);

}