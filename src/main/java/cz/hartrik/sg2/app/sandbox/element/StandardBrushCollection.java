
package cz.hartrik.sg2.app.sandbox.element;

import cz.hartrik.sg2.brush.build.BrushCollectionBuilder;
import cz.hartrik.sg2.brush.manage.BrushCollection;
import cz.hartrik.sg2.brush.manage.BrushInfoLoader;
import cz.hartrik.sg2.brush.manage.BrushManager;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;

/**
 *
 * @version 2017-08-05
 * @author Patrik Harag
 */
public class StandardBrushCollection implements BrushCollection {

    static final int VERSION = 2;
    static final String PROPERTIES = "cz.hartrik.sg2.app.sandbox.element.brushes";

    @Override
    public String getName() {
        return "STANDARD";
    }

    @Override
    public String getVersion() {
        return String.valueOf(VERSION);
    }

    @Override
    public <T extends BrushManager> T create(Function<BrushCollection, T> bmFactory) {
        T brushManager = bmFactory.apply(this);

        BrushInfoLoader loader = new BrushInfoLoader(PROPERTIES);
        BrushCollectionBuilder builder = new BrushCollectionBuilder(loader);

        CollectionBasic.createBrushes(builder);
        CollectionSource.createBrushes(builder, brushManager);
        CollectionTest.createBrushes(builder, brushManager);
        CollectionThermalTools.createBrushes(builder);

        brushManager.addBrushItems(builder.getCollection());

        return brushManager;
    }

    // podpora zpětné kompatibility

    @Override
    public IntUnaryOperator getBCConverter(String version) {
        return getBCConverter(Integer.parseInt(version));
    }

    private IntUnaryOperator getBCConverter(int version) {
        if (version == 1) {
            return (id) -> {
                if (id == 0 || id == 1) return ++id;

                return id;
            };
        } else {
            return IntUnaryOperator.identity();
        }
    }

}