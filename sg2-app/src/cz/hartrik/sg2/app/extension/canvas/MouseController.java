
package cz.hartrik.sg2.app.extension.canvas;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.jfx.JFXControls;
import cz.hartrik.sg2.engine.EngineSyncTools;
import cz.hartrik.sg2.tool.Draggable;
import cz.hartrik.sg2.tool.Tool;
import cz.hartrik.sg2.world.BrushInserter;
import cz.hartrik.sg2.world.ElementArea;
import java.util.function.Supplier;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * Zprostředkovává ovládání myší - základní kreslení pravým a levým tlačítkem.
 * Podporuje také kreslení tahem a držením. K aktivaci držení dojde po určité
 * době po zmáčknutí tlačítka myši a k deaktivaci po jeho puštění nebo pohybu
 * myši.
 *
 * @version 2017-08-12
 * @author Patrik Harag
 */
public class MouseController {

    private static final Duration MOUSE_HOLDING_DELAY = Duration.millis(300);
    private static final Duration MOUSE_HOLDING_PAUSE = Duration.millis(50);

    protected final Canvas canvas;
    protected final JFXControls controls;

    protected final Supplier<EngineSyncTools<?>> syncTools;
    protected final Supplier<ElementArea> areaSupplier;

    public MouseController(Canvas canvas, JFXControls controls,
                           Supplier<EngineSyncTools<?>> syncTools,
                           Supplier<ElementArea> areaSupplier) {

        this.canvas = canvas;
        this.controls = controls;
        this.syncTools = syncTools;
        this.areaSupplier = areaSupplier;
    }

    public void init() {
        canvas.setOnMouseDragged(this::onMouseDragged);
        canvas.setOnMousePressed(this::onMousePressed);
        canvas.setOnMouseReleased(this::onMouseReleased);
    }

    // on mouse ...

    private Timeline timeline;
    protected volatile int lastX, lastY;

    protected void onMouseDragged(MouseEvent event) {
        stopHoldTimer();

        MouseButton button = event.getButton();
        if (!isSupported(button)) return;

        int x = (int) event.getX();
        int y = (int) event.getY();

        apply(x, y, button, true);

        lastX = x;
        lastY = y;

        startHoldTimer(button);
    }

    protected void onMousePressed(MouseEvent event) {
        MouseButton button = event.getButton();
        if (!isSupported(button)) return;

        int x = (int) event.getX();
        int y = (int) event.getY();

        apply(x, y, button, false);

        lastX = x;
        lastY = y;

        stopHoldTimer();  // pro jistotu (měl by to zajistit onMouseReleased)
        startHoldTimer(button);
    }

    protected void onMouseReleased(MouseEvent event) {
        stopHoldTimer();
    }

    // další metody

    private void startHoldTimer(MouseButton button) {
        final int lastX = this.lastX;
        final int lastY = this.lastY;
        timeline = new Timeline(new KeyFrame(MOUSE_HOLDING_PAUSE, (e) -> {
            apply(lastX, lastY, button, false);
        }));

        timeline.setDelay(MOUSE_HOLDING_DELAY);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void stopHoldTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    private boolean isSupported(MouseButton button) {
        return button == MouseButton.PRIMARY || button == MouseButton.SECONDARY;
    }

    protected void apply(final int x, final int y, MouseButton button, boolean drag) {
        final Tool tool = controls.getTool(button);
        final Brush brush = controls.getBrush(button);
        final int lastX = this.lastX;
        final int lastY = this.lastY;

        if (drag && tool instanceof Draggable) {
            syncTools.get().synchronize(() -> {
                BrushInserter<?> inserter = getInserter(brush);
                ((Draggable) tool).stroke(x, y, lastX, lastY, inserter);
                inserter.finalizeInsertion();
            });

        } else {
            syncTools.get().synchronize(() -> {
                BrushInserter<?> inserter = getInserter(brush);
                tool.apply(x, y, inserter);
                inserter.finalizeInsertion();
            });
        }
    }

    protected BrushInserter<?> getInserter(Brush brush) {
        return areaSupplier.get().getInserter().with(brush, controls);
    }

}