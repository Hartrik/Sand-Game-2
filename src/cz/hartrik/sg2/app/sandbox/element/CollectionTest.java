
package cz.hartrik.sg2.app.sandbox.element;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.BrushEffect;
import cz.hartrik.sg2.brush.jfx.BrushCollectionBuilder;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.world.element.powder.Glueable;
import cz.hartrik.sg2.world.element.solid.Destructible;

/**
 * Dočasné a testovací štětce.
 *
 * @version 2016-06-15
 * @author Patrik Harag
 */
public class CollectionTest {

    static final String URL_WIP = path("Icon - WIP.png");

    private static String path(String fileName) {
        return Resources.absolutePath(fileName, CollectionTest.class);
    }

    public static void createBrushes(BrushCollectionBuilder collectionBuilder,
            BrushManager<Brush> manager) {

        collectionBuilder
            .add(new BrushEffect(
                        collectionBuilder.load(320),
                        e -> (e instanceof Destructible) ? ((Destructible) e).destroy() : null),
                    URL_WIP)

            .add(new BrushEffect(
                        collectionBuilder.load(321),
                        e -> (e instanceof Glueable) ? ((Glueable) e).glue() : null),
                    URL_WIP)
        ;
    }

}