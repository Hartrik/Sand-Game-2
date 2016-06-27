
package cz.hartrik.sg2.brush;

import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.random.RatioChance;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.element.special.Source;
import cz.hartrik.sg2.world.factory.SourceFactory;

/**
 * Vytváří zdroje podle druhého vybraného elementu.
 *
 * @version 2015-11-19
 * @author Patrik Harag
 */
public class BrushSource extends ABrushBase {

    protected static final SourceFactory factory = new SourceFactory();

    protected final int ratioChance;
    protected Source lastSource = null;

    public BrushSource(BrushInfo brushInfo, int ratioChance) {
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

        if (Wrapper.isInstance(secondary, BrushSource.class))
            return getSource(primary);
        else if (Wrapper.isInstance(primary, BrushSource.class))
            return getSource(secondary);
        else
            return null;
    }

    @Override
    public boolean isAdvanced() {
        return true;
    }

    @Override
    public boolean produces(Element element) {
        if (!(element instanceof Source)) return false;

        // po načtení mapy nefunguje
        Source source = ((Source) element);
        for (Source next : getFactory().getElements())
            if (source == next)
                return ((RatioChance) source.getChance()).getRatio() == ratioChance;

        return false;
    }

    protected Source getSource(Brush brush) {
        if (Wrapper.isInstance(brush, SourceableBrush.class)) {
            SourceableBrush sb = (SourceableBrush) Wrapper.unwrap(brush);
            return getFactory().getElement(ratioChance, sb);
        }
        return null;
    }

    public SourceFactory getFactory() {
        return factory;
    }

}