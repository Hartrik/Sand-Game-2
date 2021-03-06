
package cz.hartrik.sg2.app.sandbox.element;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.brush.BrushTemperature;
import cz.hartrik.sg2.brush.build.BrushCollectionBuilder;
import cz.hartrik.sg2.engine.Image;
import cz.hartrik.sg2.engine.Platform;

/**
 * Štětce pro práci s teplotou.
 *
 * @version 2017-08-05
 * @author Patrik Harag
 */
public class CollectionThermalTools {

    static final String URL_TEMP_UP   = path("icon - temperature up.png");
    static final String URL_TEMP_DOWN = path("icon - temperature down.png");

    private static String path(String fileName) {
        return Resources.absolutePath(fileName, CollectionThermalTools.class);
    }

    public static void createBrushes(BrushCollectionBuilder collectionBuilder) {

        final Image tempUp   = Platform.get().createImage(URL_TEMP_UP);
        final Image tempDown = Platform.get().createImage(URL_TEMP_DOWN);

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