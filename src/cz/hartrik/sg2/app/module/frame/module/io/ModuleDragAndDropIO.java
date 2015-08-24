package cz.hartrik.sg2.app.module.frame.module.io;

import cz.hartrik.common.ui.javafx.DragAndDropInitializer;
import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.StageModule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.io.IOManager;
import cz.hartrik.sg2.tool.ToolPasteTemplateOnce;
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
 * Umožní načtení uloženého plátna přetažením do oblasti plátna.
 * 
 * @version 2015-03-11
 * @author Patrik Harag
 */
public class ModuleDragAndDropIO implements StageModule<Frame, FrameController> {

    private final IOManager<ModularWorld, Window> ioManager;

    public ModuleDragAndDropIO(IOManager<ModularWorld, Window> ioManager) {
        this.ioManager = ioManager;
    }

    @Override
    public void init(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        ioManager.setContext(stage);
        
        Node node = controller.getCanvas().getScrollPane();
        DragAndDropInitializer.initFileDragAndDrop(node, (paths) -> {
            path(paths.get(0), stage, controller);
        });
    }

    protected void path(Path path, Frame stage, FrameController controller) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(stage);
        alert.setTitle("Otevřít");
        alert.setHeaderText("Načtení uložené pozice");
        alert.setContentText("Rozhodni zda má dojít k otevření uložené pozice "
                + "nebo k jejímu vložení do stávajícího plátna.\n"
                + "Při otevření budou neuložené změny ztraceny!");

        ButtonType buttonOpen = new ButtonType("Otevřít");
        ButtonType buttonPaste = new ButtonType("Vložit");
        ButtonType buttonClose = ButtonType.CLOSE;

        alert.getButtonTypes().setAll(buttonOpen, buttonPaste, buttonClose);

        // runLater aby před zobrazením dialogu došlo ke skrytí
        // grafiky drag and drop
        Platform.runLater(() -> {
            ButtonType result = alert.showAndWait().get();
            if (result == buttonOpen)
                open(path, stage, controller);
            else if (result == buttonPaste)
                paste(path, stage, controller);
        });
    }

    protected void open(Path path, Frame stage, FrameController controller) {
        ioManager.open(path).ifPresent(controller::setUpCanvas);
    }

    protected void paste(Path path, Frame stage, FrameController controller) {
        ioManager.open(path).ifPresent(area -> {
            ElementAreaTemplate template = new ElementAreaTemplate(area);
            ToolPasteTemplateOnce.initWithCancel(template, controller.getControls());
        });
    }

}