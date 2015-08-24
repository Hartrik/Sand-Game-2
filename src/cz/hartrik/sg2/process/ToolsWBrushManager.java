
package cz.hartrik.sg2.process;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.world.World;

/**
 * Nástroje obsahující i přístup ke správci štětců.
 * 
 * @version 2014-05-11
 * @author Patrik Harag
 */
public class ToolsWBrushManager extends Tools {

    protected BrushManager<Brush> brushManager;

    public ToolsWBrushManager(World world, BrushManager<Brush> brushManager) {
        super(world);
        this.brushManager = brushManager;
    }

    public BrushManager<Brush> getBrushManager() {
        return brushManager;
    }
    
}