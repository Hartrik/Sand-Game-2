
package cz.hartrik.sg2.app.module.misc;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Tvoří obsah buňek v listu.
 * 
 * @version 2014-05-07
 * @author Patrik Harag
 */
public class BrushCellFactory
        implements Callback<ListView<Brush>, ListCell<Brush>> {
    
    protected final Controls controls;

    public BrushCellFactory(Controls current) {
        this.controls = current;
    }
    
    @Override 
    public ListCell<Brush> call(ListView<Brush> list) {
        return new BrushCell(controls);
    }
    
}