package cz.hartrik.sg2.app.module.tools;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.app.service.Service;
import cz.hartrik.sg2.app.service.ServiceProvider;
import cz.hartrik.sg2.app.extension.io.IOManager;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.tool.ToolPasteTemplateOnce;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.template.ElementAreaTemplate;
import java.nio.file.Path;
import javafx.scene.control.MenuItem;
import javafx.stage.Window;

/**
 * Sub-modul do menu, přidá položku, která umožňuje vložit uloženou pozici
 * jako šablonu.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
@ServiceProvider
public class PasteSaveSubmodule implements MenuSubmodule {

    public static final String SERVICE_PASTE_FROM_SAVE = "paste-from-save";

    private final IOManager<? extends ElementArea, Window> manager;

    public PasteSaveSubmodule(IOManager<? extends ElementArea, Window> manager) {
        this.manager = manager;
    }

    @Override
    public MenuItem[] createMenuItems(Application app) {

        MenuItem menuItem = new MenuItem("Vložit z uložené pozice");
        menuItem.setOnAction(event -> {
            app.getServiceManager().run(SERVICE_PASTE_FROM_SAVE);
        });

        return new MenuItem[] { menuItem };
    }

    @Service(SERVICE_PASTE_FROM_SAVE)
    public void pasteFromSave(Application app) {
        app.getSyncTools().pauseBothLazy(() -> {

            Path path = manager.getUiProvider().getFileChooser()
                    .openFile(app.getStage());

            if (path == null)
                return;

            manager.load(path).ifPresent((area) -> {
                Controls controls = app.getControls();
                ElementAreaTemplate template = new ElementAreaTemplate(area);
                ToolPasteTemplateOnce.initWithCancel(template, controls);
            });
        });
    }

}