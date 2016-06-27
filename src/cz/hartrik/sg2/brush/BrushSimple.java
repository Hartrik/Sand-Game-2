
package cz.hartrik.sg2.brush;

import cz.hartrik.common.Color;
import cz.hartrik.common.reflect.TODO;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.random.RandomSuppliers;
import cz.hartrik.sg2.random.XORShiftRandom;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.factory.ISingleInputFactory;
import java.util.function.Supplier;

/**
 * Jednoduchý štětec, který vrací elementy na základě přijaté továrny.
 * Parametrem v továrně vrací náhodnou přijatou konstruktorem.
 * Hodí se pro elementy bez pevné textury, pro které je nutné vytvořit na
 * každý výskyt novou instanci.
 *
 * @version 2015-11-19
 * @author Patrik Harag
 */
@TODO("implement SourceableBrush")
public class BrushSimple extends ABrushBase {

    protected static final XORShiftRandom random = new XORShiftRandom();

    private final ISingleInputFactory<Color, ? extends Element> factory;
    private final Supplier<Color> collorSupplier;

    public BrushSimple(
            BrushInfo brushInfo,
            ISingleInputFactory<Color, ? extends Element> factory,
            Color... colors) {

        super(brushInfo);
        this.factory = factory;
        this.collorSupplier = RandomSuppliers.of(colors);
    }

    // --- Brush

    @Override
    public Element getElement(Element current) {
        return factory.apply(collorSupplier.get());
    }

    // --- SourceableBrush
//
//    @Override
//    public Supplier<Sourceable> getSourceSupplier() {
//        return (Supplier<Sourceable> & Serializable) () -> {
//            Element element = factory.apply(collorSupplier.get());
//            return (element instanceof Sourceable) ? (Sourceable) element : null;
//        };
//
//    }

}