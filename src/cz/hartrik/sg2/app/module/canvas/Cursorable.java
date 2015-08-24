
package cz.hartrik.sg2.app.module.canvas;

import cz.hartrik.sg2.tool.Tool;

/**
 * Nástroj s vlastním kurzorem.
 * 
 * @version 2014-05-02
 * @author Patrik Harag
 */
public interface Cursorable extends Tool {
    
    public Cursor createCursor(CanvasWithCursor canvas);
    
}