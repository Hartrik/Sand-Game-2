
package cz.hartrik.sg2.brush;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.RandomSuppliers;
import cz.hartrik.common.random.XORShiftRandom;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.factory.ISingleInputFactory;
import java.util.function.Supplier;

/**
 * Jednoduchý štětec, který vrací elementy na základě přijaté továrny.
 * Parametrem v továrně vrací náhodnou přijatou konstruktorem.
 * Hodí se pro elementy bez pevné textury, pro které je nutné vytvořit na
 * každý výskyt novou instanci.
 * 
 * @version 2014-08-21
 * @author Patrik Harag
 */
public class BrushSimple extends ABrushBase implements SourceableBrushDef {

    protected static final XORShiftRandom random = new XORShiftRandom();
    
    protected final ISingleInputFactory<Color, ? extends Element> factory;
    protected final Color[] colors;
    protected final Supplier<Color> collorSupplier;

    public BrushSimple(
            BrushInfo brushInfo,
            ISingleInputFactory<Color, ? extends Element> factory,
            Color... colors) {
        
        super(brushInfo);
        this.factory = factory;
        this.colors = colors;
        this.collorSupplier = RandomSuppliers.of(colors);
    }
    
    // --- Brush
    
    @Override
    public Element getElement(Element current) {
        return factory.apply(collorSupplier.get());
    }
    
}