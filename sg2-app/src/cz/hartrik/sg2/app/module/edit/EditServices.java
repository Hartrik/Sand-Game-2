
package cz.hartrik.sg2.app.module.edit;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.service.Service;
import cz.hartrik.sg2.app.service.ServiceProvider;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.world.BrushInserter;
import cz.hartrik.sg2.world.Region;

/**
 * Poskytuje základní editační služby plátna - jako smazání, naplnění
 * vybraným štětcem, ohraničení...
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
@ServiceProvider
public class EditServices {

    public static final String SERVICE_EDIT_CLEAR = "edit-clear";
    public static final String SERVICE_EDIT_FILL = "edit-fill";
    public static final String SERVICE_EDIT_BOUNDS = "edit-bounds";
    public static final String SERVICE_EDIT_BOUNDS_BH = "edit-bounds/black-hole";
    public static final String SERVICE_EDIT_BOUNDS_TOP = "edit-bounds-top";
    public static final String SERVICE_EDIT_BOUNDS_BOTTOM = "edit-bounds-bottom";
    public static final String SERVICE_EDIT_BOUNDS_CHUNK = "edit-bounds-chunk";

    // služby

    @Service(SERVICE_EDIT_CLEAR)
    public void clear(Application app) {
        fill(app.getBrushManager().getBrush(1), app);
    }

    @Service(SERVICE_EDIT_FILL)
    public void fill(Application app)  {
        fill(app.getControls().getPrimaryBrush(), app);
    }

    @Service(SERVICE_EDIT_BOUNDS)
    public void bound(Application app) {
        bound(app.getControls().getPrimaryBrush(), app);
    }

    @Service(SERVICE_EDIT_BOUNDS_BH)
    public void boundBlackHole(Application app) {
        Brush blackHoleBrush = app.getBrushManager().getBrush(202);
        bound(blackHoleBrush, app);
    }

    @Service(SERVICE_EDIT_BOUNDS_CHUNK)
    public void boundChunks(Application app) {
        boundChunks(app.getControls().getPrimaryBrush(), app);
    }

    @Service(SERVICE_EDIT_BOUNDS_TOP)
    public void boundTop(Application app) {
        Brush primaryBrush = app.getControls().getPrimaryBrush();

        app.getSyncTools().synchronize(world -> {
            Region firstLine = world.streamLines().findFirst().get();
            firstLine.getTools().fill(primaryBrush, app.getControls());
        });
    }

    @Service(SERVICE_EDIT_BOUNDS_BOTTOM)
    public void boundBottom(Application app) {
        Brush primaryBrush = app.getControls().getPrimaryBrush();

        app.getSyncTools().synchronize(world -> {
            Region lastLine = world.streamLinesReversed().findFirst().get();
            lastLine.getTools().fill(primaryBrush, app.getControls());
        });
    }

    // pomocné metody

    protected void fill(Brush brush, Application app) {
        app.getSyncTools().synchronize(
                area -> area.getTools().fill(brush, app.getControls()));
    }

    protected void bound(Brush brush, Application app) {
        app.getSyncTools().synchronize((area) -> {
            BrushInserter<?> inserter = area.getInserter().with(brush, app.getControls());

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

    protected void boundChunks(Brush brush, Application app) {
        app.getSyncTools().synchronize((area) -> {
            BrushInserter<?> inserter = area.getInserter().with(brush, app.getControls());

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

}