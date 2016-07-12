package cz.hartrik.sg2.app.module.script;

import cz.hartrik.common.Exceptions;
import cz.hartrik.common.io.Resources;
import cz.hartrik.common.ui.javafx.ExceptionDialog;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.io.FileSubmodule;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * Přidá do menu položku, která po kliknutí zobrazí filechooser k vytvoření
 * nového scriptu.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class CreateScriptSubmodule implements MenuSubmodule {

    private final Path scriptFolder;
    private final FileChooser chooserSave;

    public CreateScriptSubmodule(Path scriptFolder) {
        this.scriptFolder = scriptFolder;
        this.chooserSave = new FileChooser();

        chooserSave.setTitle("Vytvořit nový script");
        chooserSave.setInitialDirectory(scriptFolder.toFile());
        chooserSave.getExtensionFilters()
                .add(new ExtensionFilter("JavaScript", "*.js"));
    }

    @Override
    public MenuItem[] createMenuItems(Application app) {

        MenuItem item = new MenuItem("Vytvořit nový", loadImage());

        item.setOnAction(e -> {
            app.getSyncTools().pauseBothLazy(() -> {
                File file = chooserSave.showSaveDialog(app.getStage());
                if (file == null) return;

                if (create(file.toPath(), app.getStage()))
                    editScript(file);
            });
        });

        return new MenuItem[] { item };
    }

    private boolean create(Path path, Window owner) {
        byte[] data = JSPublicAPI.defaultCode().getBytes(StandardCharsets.UTF_8);
        OpenOption[] options = { StandardOpenOption.CREATE,
                                 StandardOpenOption.WRITE };
        try {
            Files.write(path, data, options);

        } catch (IOException ex) {
            ExceptionDialog dialog = new ExceptionDialog(ex);
            dialog.initOwner(owner);
            dialog.setTitle("Došlo k chybě");
            dialog.setHeaderText("Chyba při vytváření souboru");
            dialog.setContentText("Došlo k neočekávané chybě vytváření souboru.");
            dialog.showAndWait();

            return false;
        }
        return true;
    }

    private void editScript(File file) {
        if (!Exceptions.pass(() -> Desktop.getDesktop().edit(file)))
            Exceptions.silent(() -> Desktop.getDesktop().open(file));
    }

    private ImageView loadImage() {
        final String fileName = "icon - new file.png";
        return new ImageView(Resources.image(fileName, FileSubmodule.class));
    }

}