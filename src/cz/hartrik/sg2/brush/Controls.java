
package cz.hartrik.sg2.brush;

import cz.hartrik.sg2.tool.Tool;
import cz.hartrik.common.Checker;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import static cz.hartrik.common.Checker.requireNonNull;

/**
 * Nastavení tlačítek na myši.
 * 
 * @version 2014-12-27
 * @author Patrik Harag
 */
public class Controls {
    
    private final ObjectProperty<Brush> primBrush, secBrush;
    private final ObjectProperty<Tool> primTool, secTool;
    
    public Controls() {
        this(Brush.EMPTY_BRUSH, Tool.EMPTY_TOOL,
             Brush.EMPTY_BRUSH, Tool.EMPTY_TOOL);
    }
    
    public Controls(Brush primBrush, Tool primTool,
                    Brush secBrush,  Tool secTool) {
        
        this.primBrush = new SimpleObjectProperty<>(requireNonNull(primBrush));
        this.primTool = new SimpleObjectProperty<>(requireNonNull(primTool));
        this.secBrush = new SimpleObjectProperty<>(requireNonNull(secBrush));
        this.secTool = new SimpleObjectProperty<>(requireNonNull(secTool));
    }
    
    // přístupové metody - property

    public ObjectProperty<Brush> primaryBrushProperty()   { return primBrush; }
    public ObjectProperty<Brush> secondaryBrushProperty() { return secBrush; }

    public ObjectProperty<Tool> primaryToolProperty()   { return primTool; }
    public ObjectProperty<Tool> secondaryToolProperty() { return secTool; }
    
    // přístupové metody - gettery & settery
    
    public Brush getPrimaryBrush()   { return primBrush.get(); }
    public Brush getSecondaryBrush() { return secBrush.get(); }

    public Tool getPrimaryTool()   { return primTool.get(); }
    public Tool getSecondaryTool() { return secTool.get(); }

    public void setPrimaryBrush(Brush brush) {
        primBrush.set(Checker.requireNonNull(brush));
    }
    
    public void setSecondaryBrush(Brush brush) {
        secBrush.set(Checker.requireNonNull(brush));
    }

    public void setPrimaryTool(Tool tool) {
        primTool.set(Checker.requireNonNull(tool));
    }

    public void setSecondaryTool(Tool tool) {
        secTool.set(Checker.requireNonNull(tool));
    }
    
    // přístupové metody - rozšíření
    
    public enum Button { PRIMARY, SECONDARY }
    
    public void setTool(Button button, Tool tool) {
        if (button == Button.PRIMARY)
            setPrimaryTool(tool);
        else
            setSecondaryTool(tool);
    }
    
    public void setBrush(Button button, Brush brush) {
        if (button == Button.PRIMARY)
            setPrimaryBrush(brush);
        else
            setSecondaryBrush(brush);
    }
    
    public Tool getTool(Button button) {
        return (button == Button.PRIMARY)
                ? getPrimaryTool() : getSecondaryTool();
    }
    
    public Brush getBrush(Button button) {
        return (button == Button.PRIMARY)
                ? getPrimaryBrush() : getSecondaryBrush();
    }
    
}