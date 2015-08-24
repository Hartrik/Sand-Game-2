
package cz.hartrik.sg2.brush.jfx;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.tool.Tool;
import cz.hartrik.sg2.brush.Controls;
import javafx.scene.input.MouseButton;

/**
 * Nastavení tlačítek myši za použití JavyFX.
 * 
 * @version 2014-12-27
 * @author Patrik Harag
 */
public class JFXControls extends Controls {

    public JFXControls() {}
    
    public JFXControls(Brush primaryBrush,   Tool primaryTool,
                       Brush secondaryBrush, Tool secondaryTool) {
        
        super(primaryBrush, primaryTool, secondaryBrush, secondaryTool);
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