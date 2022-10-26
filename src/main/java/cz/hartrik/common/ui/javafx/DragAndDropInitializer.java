package cz.hartrik.common.ui.javafx;

import cz.hartrik.common.reflect.LibraryClass;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * @version 2015-01-30
 * @author Patrik Harag
 */
@LibraryClass
public final class DragAndDropInitializer {

    private DragAndDropInitializer() {}
    
    public static void initFileDragAndDrop(
            Node node, Consumer<List<Path>> consumer) {
        
        initFileDragAndDrop(node, consumer, TransferMode.LINK);
    }
    
    public static void initFileDragAndDrop(
            Node node, Consumer<List<Path>> consumer, TransferMode mode) {
        
        node.setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasFiles())
                event.acceptTransferModes(mode);
            else
                event.consume();
        });
        
        node.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            
            if (db.hasFiles()) {
                List<Path> paths = db.getFiles().stream()
                        .map(File::toPath)
                        .collect(Collectors.toList());
                
                consumer.accept(paths);
                
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
    
}