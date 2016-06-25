
package cz.hartrik.sg2.app.module.frame.module.io;

import cz.hartrik.common.io.QuickScreenshot;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.Registerable;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.WritableImage;
import javafx.stage.Window;

/**
 * Zajišťuje funkci snímku okna.
 *
 * @version 2016-06-25
 * @author Patrik Harag
 */
public class ScreenshotService implements Registerable, Runnable {

    private final Window window;
    private final FrameController controller;

    public ScreenshotService(Window window, FrameController controller) {
        this.window = window;
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.getSyncTools().pauseBothLazy(() -> {
            Canvas canvas = controller.getCanvas().getFxCanvas();
            WritableImage image = new WritableImage(
                    (int) canvas.getWidth(), (int) canvas.getHeight());

            canvas.snapshot(null, image);

            Path scrPath = QuickScreenshot.saveImage(image);

            if (scrPath != null) {
                Alert alert = new Alert(AlertType.NONE);
                alert.initOwner(window);
                alert.setTitle("Screenshot");
                alert.setHeaderText("Screenshot byl úspěšně uložen");
                alert.setContentText(scrPath.toString());

                ButtonType buttonOpen = new ButtonType("Otevřít složku");
                ButtonType buttonOk = new ButtonType("Ok", ButtonData.OK_DONE);

                alert.getButtonTypes().setAll(buttonOpen, buttonOk);
                alert.showAndWait()
                        .filter(result -> result == buttonOpen)
                        .ifPresent(result -> open(scrPath));
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(window);
                alert.setTitle("Chyba!");
                alert.setHeaderText("Chyba při ukládání screenshotu");
                alert.setContentText(
                        "Došlo k neočekávané chybě při ukládání screenshotu.");
                alert.showAndWait();
            }
        });
    }

    private void open(Path scrPath) {
        try {
            File folder = scrPath.toFile().getParentFile();
            Desktop.getDesktop().open(folder);
        } catch (IOException ex) {
            // nevadí
        }
    }

    // registrační metoda

    @Override
    public void register(ServiceManager manager) {
        manager.register(IOServices.SERVICE_SCREENSHOT, this);
    }

}