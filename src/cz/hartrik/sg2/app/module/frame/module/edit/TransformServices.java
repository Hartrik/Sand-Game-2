
package cz.hartrik.sg2.app.module.frame.module.edit;

import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.Registerable;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import javafx.application.Platform;

import static cz.hartrik.sg2.app.module.frame.module.edit.EditServices.*;

/**
 * @version 2015-03-07
 * @author Patrik Harag
 */
public class TransformServices implements Registerable {
    
    protected final FrameController controller;

    public TransformServices(FrameController controller) {
        this.controller = controller;
    }
    
    // služby
    
    public void flipVertically() {
        controller.getSyncTools().synchronize((area)
                -> area.getTools().flipVertically());
    }
    
    public void flipHorizontally() {
        controller.getSyncTools().synchronize((area)
                -> area.getTools().flipHorizontally());
    }
    
    public void rotateLeft() {
        controller.getSyncTools().pauseBothThread(() -> {
            Platform.runLater(() -> {
                controller.setUpCanvas(
                        controller.getWorld().getTools().rotateLeft());
            });
        });
    }
    
    public void rotateRight() {
        controller.getSyncTools().pauseBothThread(() -> {
            Platform.runLater(() -> {
                controller.setUpCanvas(
                        controller.getWorld().getTools().rotateRight());
            });
        });
    }
    
    // registrační metoda
    
    @Override
    public void register(ServiceManager manager) {
        manager.register(SERVICE_EDIT_FLIP_H, this::flipHorizontally);
        manager.register(SERVICE_EDIT_FLIP_V, this::flipVertically);
        manager.register(SERVICE_EDIT_ROTATE_L, this::rotateLeft);
        manager.register(SERVICE_EDIT_ROTATE_R, this::rotateRight);
    }
    
}