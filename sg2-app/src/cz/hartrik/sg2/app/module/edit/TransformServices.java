
package cz.hartrik.sg2.app.module.edit;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.service.Service;
import cz.hartrik.sg2.app.service.ServiceProvider;
import cz.hartrik.sg2.world.ModularWorld;
import javafx.application.Platform;

/**
 * Poskytuje služby pro různé transformace plátna.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
@ServiceProvider
public class TransformServices {

    public static final String SERVICE_EDIT_FLIP_V = "edit-flip-vertically";
    public static final String SERVICE_EDIT_FLIP_H = "edit-flip-horizontally";
    public static final String SERVICE_EDIT_ROTATE_L = "edit-rotate-left";
    public static final String SERVICE_EDIT_ROTATE_R = "edit-rotate-right";

    // služby

    @Service(SERVICE_EDIT_FLIP_V)
    public void flipVertically(Application app) {
        app.getSyncTools().synchronize((area)
                -> area.getTools().flipVertically());
    }

    @Service(SERVICE_EDIT_FLIP_H)
    public void flipHorizontally(Application app) {
        app.getSyncTools().synchronize((area)
                -> area.getTools().flipHorizontally());
    }

    @Service(SERVICE_EDIT_ROTATE_L)
    public void rotateLeft(Application app) {
        app.getSyncTools().synchronize((area) -> {
            final ModularWorld world = area.getTools().rotateLeft();

            Platform.runLater(() -> {
                app.setUpCanvas(world);
            });
        });
    }

    @Service(SERVICE_EDIT_ROTATE_R)
    public void rotateRight(Application app) {
        app.getSyncTools().synchronize((area) -> {
            final ModularWorld world = area.getTools().rotateRight();

            Platform.runLater(() -> {
                app.setUpCanvas(world);
            });
        });
    }

}