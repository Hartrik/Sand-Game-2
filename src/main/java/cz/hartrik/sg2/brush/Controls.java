
package cz.hartrik.sg2.brush;

import cz.hartrik.sg2.tool.Tool;

/**
 * Nastavení tlačítek na myši.
 *
 * @version 2017-07-02
 * @author Patrik Harag
 */
public interface Controls {

    enum Button { PRIMARY, SECONDARY }

    Brush getPrimaryBrush();
    Brush getSecondaryBrush();

    Tool getPrimaryTool();
    Tool getSecondaryTool();

    void setPrimaryBrush(Brush brush);
    void setSecondaryBrush(Brush brush);

    void setPrimaryTool(Tool tool);
    void setSecondaryTool(Tool tool);

    default void setTool(Button button, Tool tool) {
        if (button == Button.PRIMARY)
            setPrimaryTool(tool);
        else
            setSecondaryTool(tool);
    }

    default void setBrush(Button button, Brush brush) {
        if (button == Button.PRIMARY)
            setPrimaryBrush(brush);
        else
            setSecondaryBrush(brush);
    }

    default Tool getTool(Button button) {
        return (button == Button.PRIMARY) ? getPrimaryTool() : getSecondaryTool();
    }

    default Brush getBrush(Button button) {
        return (button == Button.PRIMARY) ? getPrimaryBrush() : getSecondaryBrush();
    }

}