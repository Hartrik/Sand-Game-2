
package cz.hartrik.sg2.app.extension.io;

import cz.hartrik.common.Checker;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import javafx.application.Platform;

/**
 * @version 2016-07-10
 * @author Patrik Harag
 * @param <T> typ ukládaných dat
 * @param <U> typ objektu, který je nutný pro výstup - např. hlavní okno, ke
 *            kterému bude vytvořen modální dialog
 */
public class IOManager<T, U> {

    private final IOProvider<T> ioProvider;
    private final UIProvider<U> uiProvider;
    private U context;
    private Path path;
    private IOManagerListener listener;

    public IOManager(IOProvider<T> dataIO, UIProvider<U> uiProvider) {
        this(null, dataIO, uiProvider);
    }

    public IOManager(Path path, IOProvider<T> io, UIProvider<U> uiProvider) {
        this.path = path;
        this.ioProvider = io;
        this.uiProvider = uiProvider;
    }

    // metody

    // --- path

    public void setPath(Path path) {
        this.path = path;
        if (listener != null) {
            listener.pathChanged(path);
        }
    }

    public Path getPath() {
        return path;
    }

    public void clearPath() {
        setPath(null);
    }

    // --- listener

    public void setListener(IOManagerListener listener) {
        this.listener = listener;
    }

    // --- ostatní gettery a settery

    protected UIProvider<U> getUIProvider() {
        return uiProvider;
    }

    protected IOProvider<T> getIOProvider() {
        return ioProvider;
    }

    protected U getContext() {
        return context;
    }

    public void setContext(U context) {
        this.context = context;
    }

    // --- operace

    /**
     * Vyvolá dialogové okno pro výběr souboru a uloží data.
     *
     * @param data ukládaná data
     */
    public void saveAs(T data) {
        saveAs(getUIProvider().getFileChooser().saveFile(getContext()), data);
    }

    /**
     * Uloží data do souboru.
     *
     * @param path cesta k souboru
     * @param data ukládaná data
     */
    public void saveAs(Path path, T data) {
        if (path != null) {
            setPath(path);
            save(data);
        }
    }

    public void save(T data) {
        if (path == null) {
            setPath(getUIProvider().getFileChooser().saveFile(getContext()));
            if (path == null) {
                return;
            }
        }

        try {
            ioProvider.getProvider(path)
                    .orElseThrow(IOException::new)
                    .save(path, data);

        } catch (IOException ex) {
            getUIProvider().onSaveIOException(ex, context);
        }
    }

    public Optional<T> open() {
        Path file = getUIProvider().getFileChooser().openFile(getContext());
        return (file != null) ? open(file) : Optional.empty();
    }

    public Optional<T> open(Path path) {
        Checker.requireNonNull(path);

        Optional<T> optional = load(path);
        if (optional.isPresent())
            setPath(path);

        return optional;
    }

    public Optional<T> load(Path path) {
        try {
            T world = ioProvider.getProvider(path)
                    .orElseThrow(() -> new IOException("Unsupported file type"))
                    .load(path);

            return Optional.of(world);

        } catch (IOException ex) {
            // runLater - protože jinak by po celou dobu zobrazení dialogu
            // zůstala viditelná např. grafika D&D
            Platform.runLater(() -> {
                getUIProvider().onLoadIOException(ex, context);
            });
        } catch (ParseException ex) {
            Platform.runLater(() -> {
                getUIProvider().onLoadParseException(ex, context);
            });
        }

        return Optional.empty();
    }

    /**
     * Zobrazí kontrolní dialog, v případě potvrzení bude vráceno
     * <code>true</code> a budou provedeny další akce.
     *
     * @return potvrzení
     */
    public boolean newFile() {
        boolean newFile = getUIProvider().newFile(context);
        if (newFile) clearPath();

        return newFile;
    }

    // gettery

    public UIProvider<U> getUiProvider() {
        return uiProvider;
    }

    public IOProvider<T> getIoProvider() {
        return ioProvider;
    }

}