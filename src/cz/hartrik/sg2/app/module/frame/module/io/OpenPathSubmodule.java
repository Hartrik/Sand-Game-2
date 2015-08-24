package cz.hartrik.sg2.app.module.frame.module.io;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.Window;

/**
 * Přidá položku do menu, který po kliknutí otevře daný soubor / složku.
 * 
 * @version 2015-03-14
 * @author Patrik Harag
 */
public class OpenPathSubmodule extends MenuSubmodule<Frame, FrameController> {
    
    private final Path path;
    private final String text;

    public OpenPathSubmodule(Path path, String text) {
        super(false);
        this.path = path;
        this.text = text;
    }
    
    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {
    
        MenuItem item = new MenuItem(text);
        item.setOnAction(e -> open(path, stage));
        
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