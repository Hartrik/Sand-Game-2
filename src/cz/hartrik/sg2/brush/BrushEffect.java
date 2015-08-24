
package cz.hartrik.sg2.brush;

import cz.hartrik.common.Checker;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.factory.IAdvancedBrushFactory;
import cz.hartrik.sg2.world.factory.ISingleInputFactory;

/**
 * Štětec, který se hodí k ovlivňování vlastností stávajících elementů.
 * 
 * @version 2015-02-13
 * @author Patrik Harag
 */
public class BrushEffect extends ABrushBase {

    protected final ISingleInputFactory<Element, ? extends Element> factory;
    protected final IAdvancedBrushFactory<? extends Element> advancedFactory;
    
    public BrushEffect(BrushInfo brushInfo,
            ISingleInputFactory<Element, ? extends Element> factory) {
        
        super(brushInfo);
        this.factory = Checker.requireNonNull(factory);
        this.advancedFactory = null;
    }
    
    public BrushEffect(BrushInfo brushInfo,
            IAdvancedBrushFactory<? extends Element> advancedFactory) {
        
        super(brushInfo);
        this.factory = null;
        this.advancedFactory = Checker.requireNonNull(advancedFactory);
    }

    @Override
    public Element getElement(Element current) {
        if (isAdvanced())
            return advancedFactory.getElement(current, -1, -1, null, null);
        else
            return factory.apply(current);
    }

    @Override
    public Element getElement(Element current, int x, int y, ElementArea area,
            Controls controls) {
        
        if (isAdvanced())
            return advancedFactory.getElement(current, x, y, area, controls);
        else
            return factory.apply(current);
    }
    
    @Override
    public boolean isAdvanced() {
        return advancedFactory != null;
    }
    
}