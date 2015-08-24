package cz.hartrik.sg2.app.module.frame.module.about;

import cz.hartrik.sg2.app.module.dialog.about.AboutDialog;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.Registerable;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import javafx.stage.Window;

/**
 * @version 2015-04-07
 * @author Patrik Harag
 */
public class AboutServices implements Registerable {
    
     public static final String SERVICE_ABOUT_DIALOG = "about";
    
    protected final Window window;
    protected final FrameController controller;
    
    private AboutDialog dialog;

    public AboutServices(Window window, FrameController frameController) {
        this.window = window;
        this.controller = frameController;
    }
    
    // služby
    
    public void aboutDialog() {
        controller.getSyncTools().pauseBothLazy(() -> {
            if (dialog == null)
                dialog = new AboutDialog(window);

            dialog.showAndWait();
        });
    }
    
    // registrační metoda
    
    @Override
    public void register(ServiceManager manager) {
        manager.register(SERVICE_ABOUT_DIALOG, this::aboutDialog);
    }
    
}
