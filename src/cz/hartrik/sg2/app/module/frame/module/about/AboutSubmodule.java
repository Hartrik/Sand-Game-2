package cz.hartrik.sg2.app.module.frame.module.about;

import cz.hartrik.sg2.app.module.dialog.about.AboutDialog;
import cz.hartrik.sg2.app.module.frame.Application;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.service.Require;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

/**
 * Sub-modul přidávající do menu položku, která zobrazuje okno s informacemi o
 * aplikaci.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
@Require(AboutServices.class)
public class AboutSubmodule implements MenuSubmodule {

    @Override
    public MenuItem[] createMenuItems(Application app) {

        MenuItem item = new MenuItem("O programu");
        item.setGraphic(new ImageView(AboutDialog.ICON.get()));
        item.setAccelerator(KeyCombination.keyCombination("F1"));
        item.setOnAction((e) -> {
            app.getServiceManager().run(AboutServices.SERVICE_ABOUT_DIALOG);
        });

        return new MenuItem[] { item };
    }

}