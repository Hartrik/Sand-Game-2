
package cz.hartrik.common.ui.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * Třída pro jednodušší načítání FXML.
 *
 * @version 2015-07-02
 * @author Patrik Harag
 */
public class SimpleFXMLLoader {

    private final Object root;
    private final Object controller;

    /**
     * Načte FXML.
     *
     * @param resource pcesta k FXML v absolutním tvaru
     *                 (např.: /com/pkg/soubor.fxml)
     */
    public SimpleFXMLLoader(String resource) {
        this(SimpleFXMLLoader.class.getResource(resource));
    }

    /**
     * Načte FXML.
     *
     * @param resource cesta k FXML v relativním nebo absolutním tvaru
     * @param context třída, která se nachází ve stejném balíčku jako FXML
     *                soubor, umožňuje načítání s relativní cestou
     */
    public SimpleFXMLLoader(String resource, Class<?> context) {
        this(context.getResource(resource));
    }

    /**
     * Načte FXML z URL.
     *
     * @param url URL k FXML souboru
     */
    public SimpleFXMLLoader(URL url) {
        final FXMLLoader fxmlLoader = new FXMLLoader(url);

        try {
            root = fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        controller = fxmlLoader.getController();
    }

    /**
     * Načte FXML z URL.
     *
     * @param url URL k FXML souboru
     * @param bundle resources
     */
    public SimpleFXMLLoader(URL url, ResourceBundle bundle) {
        final FXMLLoader fxmlLoader = new FXMLLoader(url, bundle);

        try {
            root = fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        controller = fxmlLoader.getController();
    }

    /**
     * Vrátí kontroler k načtené hierarchii objektů.
     *
     * @param <T> typ kontroleru
     * @return kontroler
     */
    @SuppressWarnings("unchecked")
    public <T> T getController() {
        return (T) controller;
    }

    /**
     * Vrátí hierarchii objektů.
     *
     * @param <T> typ kořene
     * @return kořen hierarchie objektů
     */
    @SuppressWarnings("unchecked")
    public <T extends Node> T getRoot() {
        return (T) root;
    }

}