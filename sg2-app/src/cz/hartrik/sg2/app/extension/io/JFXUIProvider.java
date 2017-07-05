
package cz.hartrik.sg2.app.extension.io;

import cz.hartrik.common.ui.javafx.ExceptionDialog;
import cz.hartrik.sg2.app.Strings;
import java.io.FileNotFoundException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

/**
 * Zajišťuje uživatelské rozhraní v JavaFX.
 * 
 * @version 2017-07-05
 * @author Patrik Harag
 * @param <T> kontext
 */
public class JFXUIProvider<T extends Window> implements UIProvider<T> {

    private final IFileChooser<T> fileChooser;

    public JFXUIProvider(IFileChooser<T> fileChooser) {
        this.fileChooser = fileChooser;
    }

    @Override
    public IFileChooser<T> getFileChooser() {
        return fileChooser;
    }

    @Override
    public void onSaveIOException(Exception e, T context) {
        ExceptionDialog dialog = new ExceptionDialog(e);
        dialog.initOwner(context);
        dialog.setTitle(Strings.get("extension.io.err-save.title"));
        dialog.setHeaderText(Strings.get("extension.io.err-save.header"));

        if (e instanceof FileNotFoundException)
            dialog.setContentText(Strings.get("extension.io.err-save.content.fnf"));
        else
            dialog.setContentText(Strings.get("extension.io.err-save.content"));
        
        dialog.showAndWait();
    }

    @Override
    public void onLoadIOException(Exception e, T context) {
        ExceptionDialog dialog = new ExceptionDialog(e);
        dialog.initOwner(context);
        dialog.setTitle(Strings.get("extension.io.err-load.title"));
        dialog.setHeaderText(Strings.get("extension.io.err-load.header"));
        
        if (e instanceof FileNotFoundException)
            dialog.setContentText(Strings.get("extension.io.err-load.content.fnf"));
        else
            dialog.setContentText(Strings.get("extension.io.err-load.content"));
        
        dialog.showAndWait();
    }

    @Override
    public void onLoadParseException(ParseException e, T context) {
        ExceptionDialog dialog = new ExceptionDialog(e);
        dialog.initOwner(context);
        dialog.setTitle(Strings.get("extension.io.err-load.title"));
        dialog.setHeaderText(Strings.get("extension.io.err-load.header"));
        dialog.setContentText(Strings.get("extension.io.err-load.content.parse"));
        dialog.showAndWait();
    }

    @Override
    public boolean newFile(T context) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(context);
        
        alert.setTitle(Strings.get("extension.io.new.title"));
        alert.setHeaderText(Strings.get("extension.io.new.header"));
        alert.setContentText(Strings.get("extension.io.new.content"));

        return alert.showAndWait().map(b -> b == ButtonType.OK).orElse(false);
    }

}