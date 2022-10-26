package cz.hartrik.sg2.brush.manage;

import java.util.function.Function;
import java.util.function.IntUnaryOperator;

/**
 * @author Patrik Harag
 * @version 2017-08-05
 */
public interface BrushCollection {

    /**
     * Vrátí jméno kolekce.
     *
     * @return jméno
     */
    String getName();

    /**
     * Vrátí aktuální verzi.
     *
     * @return verze
     */
    String getVersion();

    /**
     * Načte a přidá správci celou kolekci štětců.
     *
     * @param <T>
     * @param bmSupplier
     * @return
     */
    <T extends BrushManager> T create(Function<BrushCollection, T> bmSupplier);

    IntUnaryOperator getBCConverter(String version);

}
