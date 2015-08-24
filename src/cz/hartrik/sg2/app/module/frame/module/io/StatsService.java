package cz.hartrik.sg2.app.module.frame.module.io;

import cz.hartrik.sg2.app.module.dialog.stats.StatsDialog;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.Registerable;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.frame.module.edit.EditServices;
import cz.hartrik.sg2.world.ElementArea;
import javafx.stage.Window;

/**
 * @version 2015-04-07
 * @author Patrik Harag
 */
public class StatsService implements Registerable {
    
    protected final Window window;
    protected final FrameController controller;

    public StatsService(Window window, FrameController frameController) {
        this.window = window;
        this.controller = frameController;
    }
    
    // služby
    
    public void showStats() {
        controller.getSyncTools().pauseBothLazy(() -> {
            final ElementArea area = controller.getWorld();
            final StatsDialog dialog = new StatsDialog(window, area);
            dialog.showAndWait();
        });
    }
    
    // registrační metoda
    
    @Override
    public void register(ServiceManager manager) {
        manager.register(EditServices.SERVICE_STATS, this::showStats);
    }
    
}