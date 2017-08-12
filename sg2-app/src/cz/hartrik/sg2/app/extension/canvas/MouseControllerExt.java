
package cz.hartrik.sg2.app.extension.canvas;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.jfx.JFXControls;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.engine.EngineSyncTools;
import cz.hartrik.sg2.tool.Draggable;
import cz.hartrik.sg2.tool.Fillable;
import cz.hartrik.sg2.tool.Tool;
import cz.hartrik.sg2.world.BrushInserter;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.element.Air;
import java.util.function.Supplier;
import java.util.logging.Logger;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Kromě základního kreslení myší umožňuje ještě podržením shift, ctrl, alt...
 *
 * @version 2017-08-12
 * @author Patrik Harag
 */
public class MouseControllerExt extends MouseControllerPick {

    private static final Logger LOGGER = Logger.getLogger(MouseControllerExt.class.getName());

    private final CanvasWithCursor fxCanvas;

    private boolean shiftEvent;
    private boolean ctrlEvent;
    private boolean altEvent;

    public MouseControllerExt(Canvas canvas, JFXControls controls,
            Supplier<ElementArea> areaSupplier,
            Supplier<EngineSyncTools<?>> syncTools,
            BrushManager brushManager, CanvasWithCursor fxCanvas) {

        super(canvas, controls, areaSupplier, syncTools, brushManager);

        this.fxCanvas = fxCanvas;
    }

    // on mouse...

    @Override
    protected void onMousePressed(MouseEvent event) {
        altEvent = event.isAltDown();

        if (event.isShiftDown() && isSupportedForDrag(event.getButton())) {
            shiftEvent = true;
            savePos(event);
            fxCanvas.setCursor(new LineIndicator(fxCanvas, lastX, lastY));
        } else if (event.isControlDown() && isSupportedForFill(event.getButton())) {
            ctrlEvent = true;
            savePos(event);
            fxCanvas.setCursor(new RectangleIndicator(fxCanvas, lastX, lastY));
        } else {
            super.onMousePressed(event);
        }
    }

    @Override
    protected void onMouseReleased(MouseEvent event) {
        if (shiftEvent || ctrlEvent) {
            apply((int) event.getX(), (int) event.getY(), event.getButton(), false);
            shiftEvent = false;
            ctrlEvent = false;

        } else {
            super.onMouseReleased(event);
        }
    }

    @Override
    protected void onMouseDragged(MouseEvent event) {
        if (!shiftEvent && !ctrlEvent)
            super.onMouseDragged(event);
    }

    // ostatní metody

    @Override
    protected void apply(int x, int y, MouseButton button, boolean drag) {
        if (shiftEvent)
            shiftEvent(x, y, button);
        else if (ctrlEvent)
            ctrlEvent(x, y, button);
        else
            super.apply(x, y, button, drag);
    }

    private void shiftEvent(int x, int y, MouseButton button) {
        final Tool tool = controls.getTool(button);
        final Brush brush = controls.getBrush(button);
        final int lastX = this.lastX;
        final int lastY = this.lastY;

        if (tool instanceof Draggable) {
            syncTools.get().synchronize(() -> {
                BrushInserter<?> inserter = getInserter(brush);
                ((Draggable) tool).stroke(x, y, lastX, lastY, inserter);
                inserter.finalizeInsertion();
            });
        } else {
            LOGGER.warning("Stroke is not supported");
        }

        setDefaultCursor();
    }

    private void ctrlEvent(int x, int y, MouseButton button) {

        final int startX = Math.min(x, lastX);
        final int startY = Math.min(y, lastY);

        final int width  = Math.abs(x - lastX);
        final int height = Math.abs(y - lastY);

        if (width != 0 && height != 0) {
            final Tool tool = controls.getTool(button);
            final Brush brush = controls.getBrush(button);

            if (tool instanceof Fillable) {
                syncTools.get().synchronize(() -> {
                    BrushInserter<?> inserter = getInserter(brush);
                    ((Fillable) tool).apply(startX, startY, width, height, inserter);
                    inserter.finalizeInsertion();
                });
            } else {
                LOGGER.warning("Fill is not supported");
            }
        }

        setDefaultCursor();
    }

    private void setDefaultCursor() {
        fxCanvas.removeCursor();
        Tool tool = controls.getTool(MouseButton.PRIMARY);
        if (tool instanceof Cursorable)
            fxCanvas.setCursor(((Cursorable) tool).createCursor(fxCanvas));
    }

    private boolean isSupportedForDrag(MouseButton button) {
        return (button == MouseButton.PRIMARY || button == MouseButton.SECONDARY)
                && controls.getTool(button) instanceof Draggable;
    }

    private boolean isSupportedForFill(MouseButton button) {
        return (button == MouseButton.PRIMARY || button == MouseButton.SECONDARY)
                && controls.getTool(button) instanceof Fillable;
    }

    private void savePos(MouseEvent event) {
        lastX = (int) event.getX();
        lastY = (int) event.getY();
    }

    @Override
    protected BrushInserter<?> getInserter(Brush brush) {
        if (altEvent) {
            ElementArea area = areaSupplier.get();
            BrushInserter<?> inserter = area.getInserter().with(brush, controls);

            inserter.appendTest((e, x, y) -> area.get(x, y) instanceof Air);
            inserter.setEraseTemperature(false);
            return inserter;

        } else {
            return super.getInserter(brush);
        }
    }

}