
package cz.hartrik.sg2.brush;

import cz.hartrik.common.random.RatioChance;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.element.special.Source;
import cz.hartrik.sg2.world.factory.SourceFactory;

/**
 * Vytváří zdroje podle druhého vybraného elementu.
 * 
 * @version 2014-08-21
 * @author Patrik Harag
 */
public abstract class ABrushSource extends ABrushBase {
    
    protected final int ratioChance;
    protected Source lastSource = null;
    
    public ABrushSource(BrushInfo brushInfo, int ratioChance) {
        super(brushInfo);
        this.ratioChance = ratioChance;
    }

    @Override
    public Element getElement(Element current) {
        return current == lastSource ? null : lastSource;
    }
    
    @Override
    public Element getElement(Element current, int x, int y, ElementArea area,
            Controls controls) {

        if (controls == null) return null;
        
        final Brush primary = controls.getPrimaryBrush();
        final Brush secondary = controls.getSecondaryBrush();
        
        if (secondary instanceof ABrushSource)
            return getSource(primary);
        else if (primary instanceof ABrushSource)
            return getSource(secondary);
        else
            return null;
    }

    @Override
    public boolean isAdvanced() {
        return true;
    }
    
    @Override
    public boolean isProducer(Element element) {
        if (!(element instanceof Source)) return false;
        
        // po načtení mapy nefunguje
        Source source = ((Source) element);
        for (Source next : getFactory().getElements())
            if (source == next)
                return ((RatioChance) source.getChance())
                        .getRatio() == ratioChance;
        
        return false;
    }
    
    protected Source getSource(Brush brush) {
        return (brush instanceof SourceableBrush)
                ? getFactory().getElement(ratioChance, (SourceableBrush) brush)
                : null;
    }
    
    public abstract SourceFactory getFactory();
    
}