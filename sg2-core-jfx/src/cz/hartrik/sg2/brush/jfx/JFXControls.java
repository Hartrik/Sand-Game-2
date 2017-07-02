
package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.common.Checker;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.tool.Tool;
import cz.hartrik.sg2.brush.Controls;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseButton;

import static cz.hartrik.common.Checker.requireNonNull;

/**
 * Nastavení tlačítek myši za použití JavyFX.
 * 
 * @version 2017-07-02
 * @author Patrik Harag
 */
public class JFXControls implements Controls {

    private final ObjectProperty<Brush> primBrush, secBrush;
    private final ObjectProperty<Tool> primTool, secTool;

    public JFXControls() {
        this(Brush.EMPTY_BRUSH, Tool.EMPTY_TOOL, Brush.EMPTY_BRUSH, Tool.EMPTY_TOOL);
    }
    
    public JFXControls(Brush primaryBrush,   Tool primaryTool,
                       Brush secondaryBrush, Tool secondaryTool) {

        this.primBrush = new SimpleObjectProperty<>(requireNonNull(primaryBrush));
        this.primTool = new SimpleObjectProperty<>(requireNonNull(primaryTool));
        this.secBrush = new SimpleObjectProperty<>(requireNonNull(secondaryBrush));
        this.secTool = new SimpleObjectProperty<>(requireNonNull(secondaryTool));
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

    // další přístupové metody
    
    public Brush getBrush(MouseButton button) {
        if (button == MouseButton.PRIMARY) return getPrimaryBrush();
        if (button == MouseButton.SECONDARY) return getSecondaryBrush();
        return null;
    }

    public Tool getTool(MouseButton button) {
        if (button == MouseButton.PRIMARY) return getPrimaryTool();
        if (button == MouseButton.SECONDARY) return getSecondaryTool();
        return null;
    }

}