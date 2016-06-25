
package cz.hartrik.sg2.app.module.canvas;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.tool.Tool;
import cz.hartrik.sg2.tool.Draggable;
import cz.hartrik.sg2.world.BrushInserter;
import cz.hartrik.sg2.brush.jfx.JFXControls;
import cz.hartrik.sg2.engine.JFXEngine;
import cz.hartrik.sg2.world.ElementArea;
import java.util.function.Supplier;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Zprostředkovává ovládání myší - základní kreslení pravým a levým tlačítkem.
 *
 * @version 2015-01-11
 * @author Patrik Harag
 */
public class MouseController {

    protected final Canvas canvas;
    protected final JFXControls controls;

    protected final Supplier<JFXEngine<?>> engineSupplier;
    protected final Supplier<ElementArea> areaSupplier;

    protected boolean drag;
    protected int lastX, lastY;

    public MouseController(Canvas canvas, JFXControls controls,
            Supplier<JFXEngine<?>> engineSupplier,
            Supplier<ElementArea> areaSupplier) {

        this.canvas = canvas;
        this.controls = controls;
        this.engineSupplier = engineSupplier;
        this.areaSupplier = areaSupplier;
    }

    public void init() {
        canvas.setOnMouseDragged(this::onMouseDragged);
        canvas.setOnMousePressed(this::onMousePressed);
        canvas.setOnMouseReleased(this::onMouseReleased);
    }

    // on mouse ...

    protected void onMouseDragged(MouseEvent event) {
        MouseButton button = event.getButton();
        if (!isSupported(button)) return;

        int x = (int) event.getX();
        int y = (int) event.getY();
        apply(x, y, button);

        lastX = x; lastY = y; drag = true;
    }

    protected void onMousePressed(MouseEvent event) {
        MouseButton button = event.getButton();
        if (!isSupported(button)) return;

        drag = false;
        apply((int) event.getX(), (int) event.getY(), button);
    }

    protected void onMouseReleased(MouseEvent event) {
        drag = false;
    }

    // další metody

    protected boolean isSupported(MouseButton button) {
        return button == MouseButton.PRIMARY || button == MouseButton.SECONDARY;
    }

    protected void apply(int x, int y, MouseButton button) {
        final Tool tool = controls.getTool(button);
        final Brush brush = controls.getBrush(button);
        final BrushInserter<?> inserter = getInserter(brush);

        if (drag && tool instanceof Draggable) {
            Draggable draggable = ((Draggable) tool);
            draggable.stroke(x, y, lastX, lastY, inserter);
        } else {
            tool.apply(x, y, inserter);
        }
        inserter.finalizeInsertion();
    }

    protected BrushInserter<?> getInserter(Brush brush) {
        return areaSupplier.get().getInserter().with(brush, controls);
    }

}