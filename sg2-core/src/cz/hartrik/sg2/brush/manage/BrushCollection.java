package cz.hartrik.sg2.brush.manage;

import cz.hartrik.sg2.engine.process.Tools;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

/**
 * @author Patrik Harag
 * @version 2017-07-19
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
     * @param toolSupplier
     * @return
     */
    <T extends BrushManager> T create(
            Function<BrushCollection, T> bmSupplier, Supplier<Tools> toolSupplier);

    IntUnaryOperator getBCConverter(String version);

}
