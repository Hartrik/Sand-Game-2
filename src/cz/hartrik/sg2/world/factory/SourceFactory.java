
package cz.hartrik.sg2.world.factory;

import cz.hartrik.common.Color;
import cz.hartrik.common.ColorTools;
import cz.hartrik.common.IntPair;
import cz.hartrik.common.random.RatioChance;
import cz.hartrik.sg2.brush.SourceableBrush;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.special.Source;
import cz.hartrik.sg2.world.element.special.Sourceable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Vytváří a uchovává zdroje.
 * 
 * @version 2014-09-12
 * @author Patrik Harag
 */
public class SourceFactory implements IElementFactory<Source> {
    
    private static final long serialVersionUID = 83715083867368_10_004L;
    
    private final Map<IntPair<SourceableBrush>, Source> sources;

    public SourceFactory() {
        this.sources = new HashMap<>();
    }
    
    public Source getElement(int ratioChance, SourceableBrush brush) {
        IntPair<SourceableBrush> pair = IntPair.of(ratioChance, brush);
        
        if (sources.containsKey(pair)) {
            return sources.get(pair);
        
        } else {
            Supplier<Sourceable> supplier = brush.getSourceSupplier();
            
            if (supplier == null || !test(supplier, 10)) { // zdroj nelze vytvořit
                sources.put(pair, null); // podruhé se to již zkoušet nebude
                return null;
            } else {
                
                final Color color = createColor(supplier);
                final RatioChance chance = new RatioChance(ratioChance);
                
                Source nSource = new Source(color, chance, supplier);
                sources.put(pair, nSource);
                return nSource;
            }
        }
    }

    protected boolean test(Supplier<Sourceable> supplier, int count) {
        for (int i = 0; i < count; i++) {
            if (supplier.get() == null) return false;
        }
        return true;
    }
    
    @Override
    public Collection<Source> getElements() {
        return sources.values();
    }

    @Override
    public void clear() {
        sources.clear();
    }
    
    protected static <E extends Element> Color createColor(Supplier<E> supplier) {
        final int cycles = 10; // prvních 10 barev pro představu stačí :)
        final Color[] colors = new Color[cycles];
        
        for (int i = 0; i < cycles; i++) {
            E element = supplier.get();
            colors[i] = (element == null ? Color.WHITE : element.getColor());
        }
        
        return ColorTools.average(colors);
    }
    
}