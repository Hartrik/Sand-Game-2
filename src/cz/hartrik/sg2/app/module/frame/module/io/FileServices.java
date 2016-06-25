
package cz.hartrik.sg2.app.module.frame.module.io;

import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.Registerable;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.io.IOManager;
import cz.hartrik.sg2.world.ModularWorld;
import javafx.application.Platform;
import javafx.stage.Window;

import static cz.hartrik.sg2.app.module.frame.module.io.IOServices.*;

/**
 * IO operace.
 * 
 * @version 2015-04-07
 * @author Patrik Harag
 */
public class FileServices implements Registerable {
    
    private final FrameController controller;
    private final IOManager<ModularWorld, Window> manager;

    public FileServices(IOManager<ModularWorld, Window> manager,
            FrameController controller) {

        this.manager = manager;
        this.controller = controller;
    }

    public synchronized void actionNew() {
        if (controller.getWorld() != null) {
            if (!manager.newFile()) return;
        } else 
            manager.clearPath();

        controller.setUpCanvas(800, 500);
    }

    public synchronized void actionOpen() {
        sync(() -> manager.open().ifPresent(controller::setUpCanvas));
    }

    public synchronized void actionSave() {
        sync(() -> manager.save(controller.getWorld()));
    }

    public synchronized void actionSaveAs() {
        sync(() -> manager.saveAs(controller.getWorld()));
    }
    
    private void sync(Runnable runnable) {
        boolean rendererRunning = !controller.getEngine().isRendererStopped();
        boolean processorRunning = !controller.getEngine().isProcessorStopped();
        
        controller.getSyncTools().stopBothAsync(() -> {
            Platform.runLater(() -> {
                runnable.run();
                if (rendererRunning) controller.getEngine().rendererStart();
                if (processorRunning) controller.getEngine().processorStart();
            });
        });
    }
    
    // registrační metoda
    
    @Override
    public void register(ServiceManager manager) {
        manager.register(SERVICE_FILE_NEW, this::actionNew);
        manager.register(SERVICE_FILE_OPEN, this::actionOpen);
        manager.register(SERVICE_FILE_SAVE, this::actionSave);
        manager.register(SERVICE_FILE_SAVE_AS, this::actionSaveAs);
    }
    
}