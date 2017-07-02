package cz.hartrik.sg2.world;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;
import java.util.function.Predicate;

/**
 * @version 2015-03-28
 * @author Patrik Harag
 */
public interface RegionTools {
    
    // fill
    
    /**
     * Naplní celé pole jedním elementem.
     * 
     * @param element element, kterým se bude plnit
     * @throws NullPointerException element je <code>null</code>
     */
    public void fill(Element element);
    
    /**
     * Naplní celé pole pomocí štětce.
     * 
     * @param brush "štětec"
     */
    public default void fill(Brush brush) {
        fill(brush, null);
    }
    
    /**
     * Naplní celé pole pomocí štětce.
     * 
     * @param brush "štětec"
     * @param controls nastavení myši, může být <code>null</code>.
     */
    public void fill(Brush brush, Controls controls);
    
    // replace
    
    /**
     * Nahradí všechny elementy, které vyprodukoval první štětec, elementy z
     * druhého štětce.
     * 
     * @param brush1 první štětec
     * @param brush2 druhý štětec
     */
    public void replace(Brush brush1, Brush brush2);
    
    /**
     * Nahradí všechny elementy, o kterých tak predikát rozhodne.
     * 
     * @param predicate predikát
     * @param brush štětec, který bude nahrazovat
     */
    public void replaceAll(Predicate<Element> predicate, Brush brush);
    
    /**
     * Nahradí všechny elementy, o kterých tak predikát rozhodne.
     * 
     * @param predicate predikát
     * @param brush štětec, který bude nahrazovat
     */
    public void replaceAll(PointElementPredicate<Element> predicate, Brush brush);
    
}