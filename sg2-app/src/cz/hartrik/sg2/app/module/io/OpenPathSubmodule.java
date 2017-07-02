package cz.hartrik.sg2.app.module.io;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.Window;

/**
 * Sub-modul do menu přidá položku, která po kliknutí otevře daný soubor / složku.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class OpenPathSubmodule implements MenuSubmodule {

    private final Path path;
    private final String text;

    public OpenPathSubmodule(Path path, String text) {
        this.path = path;
        this.text = text;
    }

    @Override
    public MenuItem[] createMenuItems(Application app) {
        MenuItem item = new MenuItem(text);
        item.setOnAction(e -> open(path, app.getStage()));

        return new MenuItem[] { item };
    }

    private void open(Path path, Window owner) {
        if (!Files.exists(path)) {
            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.initOwner(owner);
            dialog.setTitle("Varování");
            dialog.setHeaderText("Cesta nikam nevede");
            dialog.setContentText(
                    "\"" + path.toAbsolutePath() + "\" není k dispozici");

            dialog.showAndWait();

        } else {
            try {
                File file = path.toFile();
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                // nevadí
            }
        }
    }

}