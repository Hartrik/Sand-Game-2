
package cz.hartrik.sg2.app.module.edit;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.extension.dialog.size.ChangeSizeDialog;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.app.service.Require;
import cz.hartrik.sg2.app.service.ServiceManager;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

/**
 * Sub-modul do menu, přidává položku pro změnu rozměrů plátna.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@Require(SizeChangeService.class)
public class SizeChangeSubmodule implements MenuSubmodule {

    @Override
    public MenuItem[] createMenuItems(Application app) {
        ServiceManager manager = app.getServiceManager();

        Image icon = Resources.image(ChangeSizeDialog.ICON, ChangeSizeDialog.class);

        MenuItem iChangeSize = new MenuItem("Změnit rozměry plátna");
        iChangeSize.setGraphic(new ImageView(icon));
        iChangeSize.setAccelerator(KeyCombination.valueOf("shift+r"));
        iChangeSize.setOnAction((e) -> {
            manager.run(SizeChangeService.SERVICE_CHANGE_SIZE);
        });

        return new MenuItem[] { iChangeSize };
    }

}