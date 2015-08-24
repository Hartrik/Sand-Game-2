package cz.hartrik.sg2.app.module.frame.module.script;

import cz.hartrik.common.Exceptions;
import cz.hartrik.common.io.Resources;
import cz.hartrik.common.ui.javafx.ExceptionDialog;
import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.frame.module.io.FileSubmodule;
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

/**
 * Přidá do menu položku, která po kliknutí zobrazí filechooser k vytvoření
 * nového scriptu.
 * 
 * @version 2015-03-27
 * @author Patrik Harag
 */
public class CreateScriptSubmodule extends MenuSubmodule<Frame, FrameController> {
    
    private final Path scriptFolder;
    private final FileChooser chooserSave;
    
    public CreateScriptSubmodule(Path scriptFolder) {
        super(false);
        this.scriptFolder = scriptFolder;
        this.chooserSave = new FileChooser();
        
        chooserSave.setTitle("Vytvořit nový script");
        chooserSave.setInitialDirectory(scriptFolder.toFile());
        chooserSave.getExtensionFilters()
                .add(new ExtensionFilter("JavaScript", "*.js"));
    }
    
    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {

        MenuItem item = new MenuItem("Vytvořit nový", loadImage());
        
        item.setOnAction(e -> {
            controller.getSyncTools().pauseBothLazy(() -> {
                File file = chooserSave.showSaveDialog(stage);
                if (file == null) return;

                if (create(file.toPath(), stage))
                    editScript(file);
            });
        });
        
        return new MenuItem[] { item };
    }
    
    private boolean create(Path path, Frame window) {
        byte[] data = JSPublicAPI.defaultCode().getBytes(StandardCharsets.UTF_8);
        OpenOption[] options = { StandardOpenOption.CREATE,
                                 StandardOpenOption.WRITE };
        try {
            Files.write(path, data, options);
            
        } catch (IOException ex) {
            ExceptionDialog dialog = new ExceptionDialog(ex);
            dialog.initOwner(window);
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