
package cz.hartrik.sg2.app.module.io;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.service.Service;
import cz.hartrik.sg2.app.service.ServiceProvider;
import cz.hartrik.sg2.app.extension.io.IOManager;
import cz.hartrik.sg2.world.ModularWorld;
import javafx.application.Platform;
import javafx.stage.Window;

/**
 * Poskytuje IO operace jako služby.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@ServiceProvider
public class FileServices {

    public static final String SERVICE_FILE_NEW     = "file-new";
    public static final String SERVICE_FILE_OPEN    = "file-open";
    public static final String SERVICE_FILE_SAVE    = "file-save";
    public static final String SERVICE_FILE_SAVE_AS = "file-save-as";

    private final IOManager<ModularWorld, Window> manager;

    public FileServices(IOManager<ModularWorld, Window> manager) {
        this.manager = manager;
    }

    @Service(SERVICE_FILE_NEW)
    public synchronized void actionNew(Application app) {
        if (app.getWorld() != null) {
            if (!manager.newFile()) return;
        } else
            manager.clearPath();

        app.setUpCanvas(800, 500);
    }

    @Service(SERVICE_FILE_OPEN)
    public synchronized void actionOpen(Application app) {
        sync(() -> manager.open().ifPresent(app::setUpCanvas), app);
    }

    @Service(SERVICE_FILE_SAVE)
    public synchronized void actionSave(Application app) {
        sync(() -> manager.save(app.getWorld()), app);
    }

    @Service(SERVICE_FILE_SAVE_AS)
    public synchronized void actionSaveAs(Application app) {
        sync(() -> manager.saveAs(app.getWorld()), app);
    }

    private void sync(Runnable runnable, Application app) {
        boolean rendererRunning = !app.getEngine().isRendererStopped();
        boolean processorRunning = !app.getEngine().isProcessorStopped();

        app.getSyncTools().stopBothAsync(() -> {
            Platform.runLater(() -> {
                runnable.run();
                if (rendererRunning) app.getEngine().rendererStart();
                if (processorRunning) app.getEngine().processorStart();
            });
        });
    }

}