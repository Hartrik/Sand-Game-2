package cz.hartrik.sg2.brush.manage;

import java.util.function.IntUnaryOperator;

/**
 * Rozhraní, které umožňuje zpětnou kompatibilitu.
 * 
 * @version 2015-03-13
 * @author Patrik Harag
 */
public interface BackwardCompatible {
    
    /**
     * Vrátí funkci, která dokáže převést ID štětců ze starší verze do verze
     * aktuální.
     * 
     * @param version stará verze, ze které se bude převádět
     * @return konvertor
     */
    public IntUnaryOperator getConvertor(String version)
            throws UnsupportedOperationException;
    
    /**
     * Vrátí nejnovější (aktuální) verzi kolekce štětců.
     * 
     * @return aktuální verze
     */
    public String getLatestVersion();
    
}