
package cz.hartrik.sg2.app.module.io;

import cz.hartrik.common.io.QuickScreenshot;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.FrameController;
import cz.hartrik.sg2.app.service.Service;
import cz.hartrik.sg2.app.service.ServiceProvider;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Window;

/**
 * Poskytuje funkci snímání plátna.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
@ServiceProvider
public class ScreenshotService {

    public static final String SERVICE_SCREENSHOT = "screenshot";

    @Service(SERVICE_SCREENSHOT)
    public void run(Application app) {
        app.getSyncTools().pauseBothLazy(() -> {
            Image screenshot = createScreenshot(app.getController());
            Path scrPath = QuickScreenshot.saveImage(screenshot);

            if (scrPath != null)
                showResulDialog(app.getStage(), scrPath);
            else
                showErrorDialog(app.getStage());
        });
    }

    private void showResulDialog(Window owner, Path screenshotPath) {
        Alert alert = new Alert(AlertType.NONE);
        alert.initOwner(owner);
        alert.setTitle("Screenshot");
        alert.setHeaderText("Screenshot byl úspěšně uložen");
        alert.setContentText(screenshotPath.toString());

        ButtonType buttonOpen = new ButtonType("Otevřít složku");
        ButtonType buttonOk = new ButtonType("Ok", ButtonData.OK_DONE);

        alert.getButtonTypes().setAll(buttonOpen, buttonOk);
        alert.showAndWait()
                .filter(result -> result == buttonOpen)
                .ifPresent(result -> open(screenshotPath));
    }

    private void showErrorDialog(Window owner) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(owner);
        alert.setTitle("Chyba!");
        alert.setHeaderText("Chyba při ukládání screenshotu");
        alert.setContentText(
                "Došlo k neočekávané chybě při ukládání screenshotu.");
        alert.showAndWait();
    }

    private void open(Path scrPath) {
        try {
            File folder = scrPath.toFile().getParentFile();
            Desktop.getDesktop().open(folder);
        } catch (IOException ex) {
            // nevadí
        }
    }

    public static Image createScreenshot(FrameController controller) {
        Canvas canvas = controller.getCanvas().getFxCanvas();
        WritableImage image = new WritableImage(
                (int) canvas.getWidth(), (int) canvas.getHeight());

        canvas.snapshot(null, image);
        return image;
    }

}