
package cz.hartrik.sg2.app.module.io;

import cz.hartrik.common.ui.javafx.DragAndDropInitializer;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.app.extension.canvas.ToolPasteTemplateOnce;
import cz.hartrik.sg2.app.module.ApplicationModule;
import cz.hartrik.sg2.io.IOManager;
import cz.hartrik.sg2.world.ModularWorld;
import cz.hartrik.sg2.world.template.ElementAreaTemplate;
import java.nio.file.Path;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

/**
 * Modul umožní načtení uloženého plátna přetažením do oblasti plátna.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class ModuleDragAndDropIO implements ApplicationModule {

    private final IOManager<ModularWorld, Window> ioManager;

    public ModuleDragAndDropIO(IOManager<ModularWorld, Window> ioManager) {
        this.ioManager = ioManager;
    }

    @Override
    public void init(Application app) {
        Window owner = app.getStage();

        ioManager.setContext(owner);

        Node node = app.getController().getCanvas().getScrollPane();
        DragAndDropInitializer.initFileDragAndDrop(node, (paths) -> {
            path(paths.get(0), owner, app);
        });
    }

    protected void path(Path path, Window owner, Application application) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(owner);
        alert.setTitle(Strings.get("module.io.d&d.title"));
        alert.setHeaderText(Strings.get("module.io.d&d.header"));
        alert.setContentText(Strings.get("module.io.d&d.content"));

        ButtonType buttonOpen = new ButtonType(Strings.get("module.io.open"));
        ButtonType buttonPaste = new ButtonType(Strings.get("module.io.paste"));
        ButtonType buttonClose = ButtonType.CLOSE;

        alert.getButtonTypes().setAll(buttonOpen, buttonPaste, buttonClose);

        // runLater aby před zobrazením dialogu došlo ke skrytí
        // grafiky drag and drop
        Platform.runLater(() -> {
            ButtonType result = alert.showAndWait().get();
            if (result == buttonOpen)
                open(path, application);
            else if (result == buttonPaste)
                paste(path, application);
        });
    }

    protected void open(Path path, Application app) {
        ioManager.open(path).ifPresent(app::setUpCanvas);
    }

    protected void paste(Path path, Application app) {
        // nepoužijeme ioManager.open, protože to by bylo považováno
        // za změnu vybraného souboru s uloženou pozicí
        ioManager.load(path).ifPresent((area) -> {
            ElementAreaTemplate template = new ElementAreaTemplate(area);
            ToolPasteTemplateOnce.initWithCancel(template, app.getControls());
        });
    }

}