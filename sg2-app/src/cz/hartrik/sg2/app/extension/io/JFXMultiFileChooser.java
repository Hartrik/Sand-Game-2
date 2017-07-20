
package cz.hartrik.sg2.app.extension.io;

import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.io.FileTypeIOProvider;
import cz.hartrik.sg2.io.IFileChooser;
import cz.hartrik.sg2.io.IOProvider;
import java.io.File;
import java.nio.file.Path;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * 
 * 
 * @version 2017-07-19
 * @author Patrik Harag
 */
public class JFXMultiFileChooser implements IFileChooser<Window> {
    
    private static final String SAVE_TITLE = Strings.get("extension.io.dialog-save");
    private static final String OPEN_TITLE = Strings.get("extension.io.dialog-open");
    
    private final FileChooser chooserSave;
    private final FileChooser chooserOpen;

    public JFXMultiFileChooser(IOProvider<?> ioProvider, Path initalDir) {
        this.chooserSave = new FileChooser();
        this.chooserOpen = new FileChooser();
        
        chooserSave.setTitle(SAVE_TITLE);
        chooserOpen.setTitle(OPEN_TITLE);
        
        chooserSave.setInitialDirectory(initalDir.toFile());
        chooserOpen.setInitialDirectory(initalDir.toFile());
        
        String[] filters = ioProvider.getProviders().stream()
                .map(provider -> "*." + provider.getExtension())
                .toArray(String[]::new);
        
        chooserOpen.getExtensionFilters().add(
                new ExtensionFilter(Strings.get("extension.io.type.all"), filters));
        
        for (FileTypeIOProvider<?> provider : ioProvider.getProviders()) {
            String info = Strings.get("extension.io.type." + provider.getExtension());
            String extension = "*." + provider.getExtension();

            ExtensionFilter filter = new ExtensionFilter(info, extension);

            chooserSave.getExtensionFilters().add(filter);
            chooserOpen.getExtensionFilters().add(filter);
        }
    }
    
    @Override
    public Path openFile(Window window) {
        final File file = chooserOpen.showOpenDialog(window);
        remember(file);
        return file == null ? null : file.toPath();
    }
    
    @Override
    public Path saveFile(Window window) {
        final File file = chooserSave.showSaveDialog(window);
        remember(file);
        return file == null ? null : file.toPath();
    }
    
    private void remember(File file) {
        if (file != null) {
            final File folder = file.getParentFile();
            chooserSave.setInitialDirectory(folder);
            chooserOpen.setInitialDirectory(folder);
        }
    }
    
}