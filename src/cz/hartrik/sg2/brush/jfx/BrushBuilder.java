
package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.sg2.brush.manage.BrushInfo;

/**
 * Předek pro buildery jednotlivých typů štětců.
 * 
 * @version 2014-05-22
 * @author Patrik Harag
 */
public abstract class BrushBuilder {
    
    protected final BrushCollectionBuilder collectionBuilder;
    protected BrushInfo brushInfo;
    protected boolean hidden = false;
    
    public BrushBuilder(BrushCollectionBuilder collectionBuilder) {
        this.collectionBuilder = collectionBuilder;
    }

    // metody
    
    public BrushBuilder setBrushInfo(BrushInfo brushInfo) {
        this.brushInfo = brushInfo;
        
        return this;
    }
    
    public BrushBuilder hidden() {
        this.hidden = true;
        
        return this;
    }
    
    public abstract BrushCollectionBuilder build();
    
}