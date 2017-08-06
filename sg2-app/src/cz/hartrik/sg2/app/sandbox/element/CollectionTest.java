
package cz.hartrik.sg2.app.sandbox.element;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.brush.BrushEffect;
import cz.hartrik.sg2.brush.build.BrushCollectionBuilder;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.world.element.powder.Glueable;
import cz.hartrik.sg2.world.element.powder.WallPowder;
import cz.hartrik.sg2.world.element.solid.Destructible;
import cz.hartrik.sg2.world.element.solid.GluedPowder;

/**
 * Dočasné a testovací štětce.
 *
 * @version 2016-07-02
 * @author Patrik Harag
 */
public class CollectionTest {

    static final String URL_WIP = path("icon - WIP.png");

    private static String path(String fileName) {
        return Resources.absolutePath(fileName, CollectionTest.class);
    }

    public static void createBrushes(BrushCollectionBuilder collectionBuilder,
            BrushManager manager) {

        BrushEffect brushDestroy = new BrushEffect(
                collectionBuilder.load(320),
                e -> (e instanceof Destructible) ? ((Destructible) e).destroy() : null);

        BrushEffect brushGlue = new BrushEffect(
                collectionBuilder.load(321),
                e -> (e instanceof Glueable) ? ((Glueable) e).glue() : null);

        brushDestroy.setPredicate(e -> e instanceof WallPowder);
        brushGlue.setPredicate(e -> e instanceof GluedPowder);

        collectionBuilder
            .add(brushDestroy, URL_WIP)
            .add(brushGlue, URL_WIP)
        ;
    }

}