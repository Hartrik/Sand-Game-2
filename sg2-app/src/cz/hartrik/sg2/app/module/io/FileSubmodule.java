
package cz.hartrik.sg2.app.module.io;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.app.service.ServiceManager;
import cz.hartrik.sg2.app.extension.io.IOManager;
import cz.hartrik.sg2.world.ModularWorld;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.stage.Window;

import static cz.hartrik.sg2.app.module.io.FileServices.*;

/**
 * Sub-modul do menu, přidává do menu IO operace. <p>
 *
 * <b> Před zobrazením okna je nutné vytvořit nový soubor! </b>
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class FileSubmodule implements MenuSubmodule {

    private final IOManager<ModularWorld, Window> ioManager;

    public FileSubmodule(IOManager<ModularWorld, Window> ioManager) {
        this.ioManager = ioManager;
    }

    @Override
    public MenuItem[] createMenuItems(Application app) {
        ServiceManager manager = app.getServiceManager();

        manager.register(new FileServices(ioManager));

        ioManager.setContext(app.getStage());

        MenuItem iNew = new MenuItem("Nový", img("icon - new file.png"));
        iNew.setOnAction((e) -> manager.run(SERVICE_FILE_NEW));
        iNew.setAccelerator(KeyCombination.valueOf("ctrl+n"));

        MenuItem iOpen = new MenuItem("Otevřít", img("icon - open.png"));
        iOpen.setOnAction((e) -> manager.run(SERVICE_FILE_OPEN));
        iOpen.setAccelerator(KeyCombination.valueOf("ctrl+o"));

        MenuItem iSave = new MenuItem("Uložit", img("icon - save.png"));
        iSave.setOnAction((e) -> manager.run(SERVICE_FILE_SAVE));
        iSave.setAccelerator(KeyCombination.valueOf("ctrl+s"));

        MenuItem iSaveAs = new MenuItem("Uložit jako", img("icon - save.png"));
        iSaveAs.setOnAction((e) -> manager.run(SERVICE_FILE_SAVE_AS));
        iSaveAs.setAccelerator(KeyCombination.valueOf("ctrl+shift+s"));

        return new MenuItem[] { iNew, iOpen, iSave, iSaveAs };
    }

    private ImageView img(String fileName) {
        return new ImageView(Resources.image(fileName, getClass()));
    }

}