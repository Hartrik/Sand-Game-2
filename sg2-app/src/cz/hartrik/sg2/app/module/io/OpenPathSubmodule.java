
package cz.hartrik.sg2.app.module.io;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.Window;

/**
 * Sub-modul do menu přidá položku, která po kliknutí otevře daný soubor / složku.
 *
 * @version 2017-07-05
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
        item.setOnAction(e -> openInDesktop(path, app.getStage()));

        return new MenuItem[] { item };
    }

    static void openInDesktop(Path path, Window owner) {
        try {
            File file = path.toFile();
            Desktop.getDesktop().open(file);

        } catch (IOException ex) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.initOwner(owner);
            dialog.setTitle(Strings.get("module.io.err-show.title"));
            dialog.setHeaderText(Strings.get("module.io.err-show.header"));
            dialog.setContentText(Strings.get(
                    "module.io.err-show.content", path.toAbsolutePath()));

            dialog.showAndWait();
        }
    }

}