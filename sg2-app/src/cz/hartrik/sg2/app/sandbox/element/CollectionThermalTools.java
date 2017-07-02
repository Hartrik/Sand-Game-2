
package cz.hartrik.sg2.app.sandbox.element;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.brush.BrushTemperature;
import cz.hartrik.sg2.brush.jfx.BrushCollectionBuilder;
import cz.hartrik.sg2.engine.process.Tools;
import java.util.function.Supplier;
import javafx.scene.image.Image;

/**
 * Štětce pro práci s teplotou.
 *
 * @version 2016-06-14
 * @author Patrik Harag
 */
public class CollectionThermalTools {

    static final String URL_TEMP_UP   = path("Icon - temperature up.png");
    static final String URL_TEMP_DOWN = path("Icon - temperature down.png");

    private static String path(String fileName) {
        return Resources.absolutePath(fileName, CollectionThermalTools.class);
    }

    public static void createBrushes(BrushCollectionBuilder collectionBuilder,
            Supplier<Tools> toolSupplier) {

        final Image tempUp   = new Image(URL_TEMP_UP);
        final Image tempDown = new Image(URL_TEMP_DOWN);

        collectionBuilder
                .add(new BrushTemperature(collectionBuilder.load(300), 200), tempUp)
                .add(new BrushTemperature(collectionBuilder.load(301), 500), tempUp)
                .add(new BrushTemperature(collectionBuilder.load(302), 1000), tempUp)
                .add(new BrushTemperature(collectionBuilder.load(303), 1500), tempUp)
                .add(new BrushTemperature(collectionBuilder.load(304), 2000), tempUp)
                .add(new BrushTemperature(collectionBuilder.load(305), 3000), tempUp)
                .add(new BrushTemperature(collectionBuilder.load(310), 0), tempDown)
            ;
    }

}