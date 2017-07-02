package cz.hartrik.sg2.world.template;

import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.Inserter;

/**
 * Rozhraní pro šablonu, která na plátno umístí elementy podle nějakého vzoru.
 * 
 * @version 2015-01-11
 * @author Patrik Harag
 */
public interface Template {
    
    /**
     * Vloží šablonu na pozici <code>0x0</code>.
     * 
     * @param area struktura elementů
     */
    public default void insert(ElementArea area) {
        insert(area, 0, 0);
    }
    
    /**
     * Vloží šablonu na danou pozici. Pozice značí umístění levého horního
     * rohu struktury. V případě, že se šablona přizpůsobuje velikosti pole
     * elementů, nemusí mít tyto hodnoty.
     * 
     * @param area pole elementů
     * @param x horizontální souřadnice
     * @param y vertikální souřadnice
     */
    public default void insert(ElementArea area, int x, int y) {
        Inserter<? extends ElementArea> inserter = area.getInserter();
        insert(area.getInserter(), x, y);
        inserter.finalizeInsertion();
    }
    
    /**
     * Vloží šablonu na danou pozici. Pozice značí umístění levého horního
     * rohu struktury. V případě, že se šablona přizpůsobuje velikosti pole
     * elementů, nemusí mít tyto hodnoty.
     * 
     * @param inserter inserter
     * @param x horizontální souřadnice
     * @param y vertikální souřadnice
     */
    public void insert(Inserter<? extends ElementArea> inserter, int x, int y);
    
    /**
     * Pokud se rozměry šablony mění na základě velikosti pole elementů, do
     * kterého se vkládá.
     * 
     * @return responsivní
     */
    public boolean isResponsive();
    
    /**
     * Vrátí šířku šablony, pokud je šířka relativní, vrací <code>-1</code>.
     * 
     * @return šířka šablony
     */
    public int getWidth();
    
    /**
     * Vrátí výšku šablony, pokud je výška relativní, vrací <code>-1</code>.
     * 
     * @return výška šablony
     */
    public int getHeight();
    
}