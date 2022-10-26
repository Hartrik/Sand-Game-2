
package cz.hartrik.sg2.app.extension.dialog.about;

import cz.hartrik.common.Exceptions;
import cz.hartrik.sg2.app.sandbox.Main;
import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Controller ovládající panel informacemi o programu.
 * 
 * @version 2017-07-10
 * @author Patrik Harag
 */
public class AboutController implements Initializable {

    private @FXML Label labelName;
    private @FXML Label labelVersion;
    private @FXML Label labelAuthor;
    private @FXML Hyperlink hyperlink;

    private @FXML WebView historyWebView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelName.setText(Main.APP_NAME);
        labelVersion.setText(String.format("%s (%s)",
                Main.APP_VERSION, Main.APP_VERSION_DATE));
        labelAuthor.setText(Main.APP_AUTHOR);

        hyperlink.setText(Main.APP_WEB);
        hyperlink.setOnAction(t -> {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)){
                Exceptions.unchecked(() -> {
                    URI uri = new URI(Main.APP_WEB);
                    desktop.browse(uri);
                });
            }
        });

        loadHistoryBeta();
    }
    
    @FXML
    public void close() {
        ((Stage) historyWebView.getScene().getWindow()).close();
    }
    
    @FXML
    public void loadHistoryAlpha() {
        loadPage(historyWebView, "version history - alpha.html");
    }
    
    @FXML
    public void loadHistoryBeta() {
        loadPage(historyWebView, "version history - beta.html");
    }
    
    private void loadPage(WebView webView, String htmlFileName) {
        URL url = getClass().getResource(htmlFileName);
        webView.getEngine().load(url.toExternalForm());
    }
    
}