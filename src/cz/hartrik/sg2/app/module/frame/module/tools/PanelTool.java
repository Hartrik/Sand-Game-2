package cz.hartrik.sg2.app.module.frame.module.tools;


import cz.hartrik.common.Exceptions;
import cz.hartrik.sg2.app.module.frame.FrameController;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

/**
 * Abstraktní třída pro panel s nastavením štětce.
 * 
 * @version 2015-03-28
 * @author Patrik Harag
 */
public abstract class PanelTool {
    
    protected final FrameController controller;
    protected final int min, max, def;
    protected Pane pane;
    
    public PanelTool(int min, int max, int def, FrameController controller) {
        this.controller = controller;
        this.min = min;
        this.max = max;
        this.def = def;
    }
    
    protected class IntegerConverter extends StringConverter<Integer> {
        
        @Override
        public String toString(Integer integer) {
            return String.valueOf(integer);
        }

        @Override
        public Integer fromString(String string) {
            String filtered = string.replaceAll("\\D+","");
            
            int n = Exceptions.call(Integer::parseInt, filtered).orElse(def);
            
            if      (n > max) n = max;
            else if (n < min) n = min;
            
            return n;
        }
    }
    
    public abstract void updateTool();
    
    public Pane getPane() {
        return pane;
    }
    
}