package cz.hartrik.sg2.app.module.edit;

import cz.hartrik.common.Pair;
import cz.hartrik.sg2.app.extension.dialog.size.ChangeSizeDialog;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.service.Service;
import cz.hartrik.sg2.app.service.ServiceProvider;
import cz.hartrik.sg2.engine.Engine;
import cz.hartrik.sg2.world.ModularWorld;

/**
 * Poskytuje službu pro změnu velikosti plátna.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
@ServiceProvider
public class SizeChangeService {

    public static final String SERVICE_CHANGE_SIZE = "change-canvas-size";

    // služby

    @Service(SERVICE_CHANGE_SIZE)
    public void changeSize(Application app) {
        final Engine<?, ?> engine = app.getEngine();

        boolean rendererStopped = engine.isRendererStopped();
        boolean processorStopped = engine.isProcessorStopped();

        engine.processorStop();
        engine.rendererStop();

        final ModularWorld world = app.getWorld();

        ChangeSizeDialog dialog = new ChangeSizeDialog(app.getStage(),
                world.getWidth(), world.getHeight(), world.getChunkSize());

        dialog.showAndWait();

        Pair<Integer, Integer> size = dialog.getSize();
        int chunkSize = dialog.getChunkSize();

        if (needsUpdate(size, chunkSize, world)) {
            ModularWorld resized = world.getTools().resize(
                    size.getFirst(), size.getSecond(), chunkSize);

            app.setUpCanvas(resized);

            if (!processorStopped) app.getEngine().processorStart();

        } else {
            if (!rendererStopped) engine.rendererStart();
            if (!processorStopped) engine.processorStart();
        }
    }

    private boolean needsUpdate(
            Pair<Integer, Integer> size, int chunkSize, ModularWorld world) {

        return (size != null) && (size.getFirst() != world.getWidth()
                || size.getSecond() != world.getHeight()
                || chunkSize != world.getChunkSize());
    }

}
