
package cz.hartrik.sg2.app.extension.io;

import cz.hartrik.common.ui.javafx.ExceptionDialog;
import java.io.FileNotFoundException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

/**
 * Zajišťuje uživatelské rozhraní v JavaFX.
 * 
 * @version 2015-03-11
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
        dialog.setTitle("Chyba při ukládání");
        dialog.setHeaderText("Chyba při ukládání");

        if (e instanceof FileNotFoundException)
            dialog.setContentText("Na tuto pozici není možné soubor uložit.");
        else
            dialog.setContentText("Došlo k neočekávané chybě při ukládání.");
        
        dialog.showAndWait();
    }

    @Override
    public void onLoadIOException(Exception e, T context) {
        ExceptionDialog dialog = new ExceptionDialog(e);
        dialog.initOwner(context);
        dialog.setTitle("Chyba při načítání");
        dialog.setHeaderText("Chyba při načítání");
        
        if (e instanceof FileNotFoundException)
            dialog.setContentText("Cesta nevede k žádnému souboru.");
        else
            dialog.setContentText("Došlo k neočekávané chybě při načítání souboru.");
        
        dialog.showAndWait();
    }

    @Override
    public void onLoadParseException(ParseException e, T context) {
        ExceptionDialog dialog = new ExceptionDialog(e);
        dialog.initOwner(context);
        dialog.setTitle("Chyba při načítání");
        dialog.setHeaderText("Chyba při načítání");
        dialog.setContentText("Došlo k neočekávané chybě při parsování souboru");
        dialog.showAndWait();
    }

    @Override
    public boolean newFile(T context) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(context);
        
        alert.setTitle("Nový soubor");
        alert.setHeaderText("Vytvořit nový soubor");
        alert.setContentText(
                "Opravdu si přejete vytvořit nový soubor?\n"
                + "Neuložená data budou smazána.");

        return alert.showAndWait().get() == ButtonType.OK;
    }

}