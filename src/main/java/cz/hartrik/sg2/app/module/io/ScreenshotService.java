
package cz.hartrik.sg2.app.module.io;

import cz.hartrik.common.io.QuickScreenshot;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.FrameController;
import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.app.service.Service;
import cz.hartrik.sg2.app.service.ServiceProvider;
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
 * @version 2017-07-05
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
        alert.setTitle(Strings.get("module.io.scr.title"));
        alert.setHeaderText(Strings.get("module.io.scr.header"));
        alert.setContentText(screenshotPath.toString());

        ButtonType buttonShow = new ButtonType(Strings.get("module.io.scr.b-show"));
        ButtonType buttonOk = new ButtonType(Strings.get("module.io.scr.b-ok"), ButtonData.OK_DONE);

        alert.getButtonTypes().setAll(buttonShow, buttonOk);
        alert.showAndWait()
                .filter(result -> result == buttonShow)
                .ifPresent(result -> OpenPathSubmodule.openInDesktop(screenshotPath, owner));
    }

    private void showErrorDialog(Window owner) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(owner);
        alert.setTitle(Strings.get("module.io.err-scr.title"));
        alert.setHeaderText(Strings.get("module.io.err-scr.header"));
        alert.setContentText(Strings.get("module.io.err-scr.content"));
        alert.showAndWait();
    }

    private static Image createScreenshot(FrameController controller) {
        Canvas canvas = controller.getCanvas().getFxCanvas();
        WritableImage image = new WritableImage(
                (int) canvas.getWidth(), (int) canvas.getHeight());

        canvas.snapshot(null, image);
        return image;
    }

}