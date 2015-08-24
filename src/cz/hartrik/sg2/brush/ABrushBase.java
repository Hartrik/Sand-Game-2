
package cz.hartrik.sg2.brush;

import cz.hartrik.sg2.brush.manage.BrushInfo;

/**
 * Abstraktní třída jako základ pro další implementaci.
 * 
 * @version 2014-05-06
 * @author Patrik Harag
 */
public abstract class ABrushBase implements Brush {
    
    private final BrushInfo brushInfo;

    public ABrushBase(BrushInfo brushInfo) {
        this.brushInfo = brushInfo;
    }
    
    @Override
    public BrushInfo getInfo() {
        return brushInfo;
    }
    
}