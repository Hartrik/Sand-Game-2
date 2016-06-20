
package cz.hartrik.sg2.app.sandbox.element;

import cz.hartrik.sg2.brush.jfx.BrushCollectionBuilder;
import cz.hartrik.sg2.brush.manage.BrushInfoLoader;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.process.Tools;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

/**
 *
 *
 * @version 2015-03-13
 * @author Patrik Harag
 */
public class StandardBrushCollection {

    static final int VERSION = 2;
    static final String PROPERTIES = "cz.hartrik.sg2.app.sandbox.element.Brushes";

    /**
     * Načte a přidá správci celou kolekci štětců.
     *
     * @param <T>
     * @param bmSupplier
     * @param toolSupplier
     * @return
     */
    public static <T extends BrushManager> T create(
            Supplier<T> bmSupplier, Supplier<Tools> toolSupplier) {

        T brushManager = bmSupplier.get();

        BrushInfoLoader loader = new BrushInfoLoader(PROPERTIES);
        BrushCollectionBuilder builder = new BrushCollectionBuilder(loader);

        CollectionBasic.createBrushes(builder);
        CollectionTest.createBrushes(builder, brushManager);
        CollectionThermalTools.createBrushes(builder, toolSupplier);

        brushManager.addBrushItems(builder.getCollection());

        return brushManager;
    }

    // podpora zpětné kompatibility

    public static IntUnaryOperator getBCConverter(String version) {
        return getBCConverter(Integer.parseInt(version));
    }

    public static IntUnaryOperator getBCConverter(int version) {
        if (version == 1) {
            return (id) -> {
                if (id == 0 || id == 1) return ++id;

                return id;
            };
        } else {
            return IntUnaryOperator.identity();
        }
    }

    public static String getVersion() {
        return String.valueOf(VERSION);
    }

}