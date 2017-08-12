
package cz.hartrik.sg2.app.extension.canvas;

import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.jfx.JFXControls;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.engine.EngineSyncTools;
import cz.hartrik.sg2.tool.Can;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import java.util.function.Supplier;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

/**
 * Kontrolor myši s kapátkem a plechovkou na prostředním tlačítku
 *
 * @version 2015-03-21
 * @author Patrik Harag
 */
public class MouseControllerPick extends MouseController {

    protected final BrushManager brushManager;
    private final Can can1;
    private final Can can2;

    public MouseControllerPick(Canvas canvas, JFXControls controls,
            Supplier<ElementArea> areaSupplier,
            Supplier<EngineSyncTools<?>> syncTools, BrushManager brushManager) {

        super(canvas, controls, syncTools, areaSupplier);
        this.brushManager = brushManager;
        this.can1 = new Can(controls::getPrimaryBrush, brushManager);
        this.can2 = new Can(controls::getSecondaryBrush, brushManager);
    }

    @Override
    protected void onMousePressed(MouseEvent event) {
        if (event.isMiddleButtonDown()) {

            final int x = (int) event.getX();
            final int y = (int) event.getY();

            if (event.isShiftDown()) {
                // plechovka 1
                syncTools.get().synchronize(
                        () -> can1.apply(x, y, getInserter(Brush.EMPTY_BRUSH)));

            } else if (event.isControlDown()) {
                // plechovka 2
                syncTools.get().synchronize(
                        () -> can2.apply(x, y, getInserter(Brush.EMPTY_BRUSH)));

            } else if (event.isAltDown()) {


            } else {
                // picker
                ElementArea area = areaSupplier.get();
                if (!area.valid(x, y)) return;

                Element element = area.get(x, y);
                Brush producer = brushManager.getProducer(element);
                if (producer != null)
                    controls.setPrimaryBrush(producer);
            }

        } else {
            super.onMousePressed(event);
        }
    }

}