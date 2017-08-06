
package cz.hartrik.sg2.app.sandbox.element;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.brush.BrushSource;
import cz.hartrik.sg2.brush.BrushSourceEffect;
import cz.hartrik.sg2.brush.build.BrushCollectionBuilder;
import cz.hartrik.sg2.brush.manage.BrushManager;


/**
 * Štětce vytvářející zdroje.
 *
 * @version 2016-07-02
 * @author Patrik Harag
 */
public class CollectionSource {

    static final String URL_PIPE = path("icon - pipe.png");

    private static String path(String fileName) {
        return Resources.absolutePath(fileName, CollectionSource.class);
    }

    public static void createBrushes(BrushCollectionBuilder collectionBuilder,
            BrushManager bm) {

        collectionBuilder
            .add(230, URL_PIPE, info -> new BrushSource(info, 100)) // 1/100 = 1 %
            .add(231, URL_PIPE, info -> new BrushSource(info, 40))  // 1/40  = 2.5 %
            .add(232, URL_PIPE, info -> new BrushSource(info, 10))  // ...
            .add(233, URL_PIPE, info -> new BrushSource(info, 1))

            .addHidden(240, info -> new BrushSourceEffect(info, 100, bm)) // 1/100 = 1 %
            .addHidden(241, info -> new BrushSourceEffect(info, 40, bm))  // 1/40  = 2.5 %
            .addHidden(242, info -> new BrushSourceEffect(info, 10, bm))  // ...
            .addHidden(243, info -> new BrushSourceEffect(info, 1, bm))
        ;
    }

}