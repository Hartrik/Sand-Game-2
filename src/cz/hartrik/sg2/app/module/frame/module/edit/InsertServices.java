
package cz.hartrik.sg2.app.module.frame.module.edit;

import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.Registerable;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.brush.jfx.JFXControls;
import cz.hartrik.sg2.world.BrushInserter;
import cz.hartrik.sg2.world.Region;

import static cz.hartrik.sg2.app.module.frame.module.edit.EditServices.*;

/**
 * @version 2016-07-03
 * @author Patrik Harag
 */
public class InsertServices implements Registerable {

    protected final FrameController controller;

    public InsertServices(FrameController controller) {
        this.controller = controller;
    }

    // pomocné metody

    protected void fill(Brush brush, Controls controls) {
        controller.getSyncTools().synchronize(
                area -> area.getTools().fill(brush, controls));
    }

    protected void bound(Brush brush, Controls controls) {
        controller.getSyncTools().synchronize((area) -> {
            BrushInserter<?> inserter = area.getInserter().with(brush, controls);

            final int maxY = area.getHeight() - 1;
            final int maxX = area.getWidth() - 1;

            // horní a dolní okraj
            for (int x = 0; x < area.getWidth(); x++) {
                inserter.apply(x, 0);
                inserter.apply(x, maxY);
            }

            // po stranách
            for (int y = 0; y < maxY; y++) {
                inserter.apply(0, y);
                inserter.apply(maxX, y);
            }

            inserter.finalizeInsertion();
        });
    }

    protected void boundChunks(Brush brush, Controls controls) {
        controller.getSyncTools().synchronize((area) -> {
            BrushInserter<?> inserter = area.getInserter().with(brush, controls);

            // horizontální
            for (int x = 0; x < area.getWidth(); x++) {
                for (int y = 0; y < area.getHeight(); y += area.getChunkSize()) {
                    inserter.apply(x, y);
                    inserter.apply(x, y + area.getChunkSize() - 1);
                }
            }

            // vertiální
            for (int y = 0; y < area.getHeight(); y++) {
                for (int x = 0; x < area.getWidth(); x += area.getChunkSize()) {
                    inserter.apply(x, y);
                    inserter.apply(x + area.getChunkSize() - 1, y);
                }
            }

            inserter.finalizeInsertion();
        });
    }

    public void boundTop(Brush brush, Controls controls) {
        controller.getSyncTools().synchronize(world -> {
            Region firstLine = world.streamLines().findFirst().get();
            firstLine.getTools().fill(brush, controls);
        });
    }

    public void boundBottom(Brush brush, Controls controls) {
        controller.getSyncTools().synchronize(world -> {
            Region lastLine = world.streamLinesReversed().findFirst().get();
            lastLine.getTools().fill(brush, controls);
        });
    }

    // služby

    public void clear() {
        fill(controller.getBrushManager().getBrush(1), controller.getControls());
    }

    public void fill()  {
        fill(controller.getControls().getPrimaryBrush(), controller.getControls());
    }

    public void boundBlackHole() {
        Brush brush = controller.getBrushManager().getBrush(202);
        bound(brush, controller.getControls());
    }

    public void bound() {
        JFXControls controls = controller.getControls();
        bound(controls.getPrimaryBrush(), controls);
    }

    public void boundChunks() {
        JFXControls controls = controller.getControls();
        boundChunks(controls.getPrimaryBrush(), controls);
    }

    public void boundTop() {
        JFXControls controls = controller.getControls();
        boundTop(controls.getPrimaryBrush(), controls);
    }

    public void boundBottom() {
        JFXControls controls = controller.getControls();
        boundBottom(controls.getPrimaryBrush(), controls);
    }

    // registrační metoda

    @Override
    public void register(ServiceManager manager) {
        manager.register(SERVICE_EDIT_CLEAR, this::clear);
        manager.register(SERVICE_EDIT_FILL, this::fill);
        manager.register(SERVICE_EDIT_BOUNDS_BH, this::boundBlackHole);
        manager.register(SERVICE_EDIT_BOUNDS, this::bound);
        manager.register(SERVICE_EDIT_BOUNDS_TOP, this::boundTop);
        manager.register(SERVICE_EDIT_BOUNDS_BOTTOM, this::boundBottom);
        manager.register(SERVICE_EDIT_BOUNDS_CHUNK, this::boundChunks);
    }

}