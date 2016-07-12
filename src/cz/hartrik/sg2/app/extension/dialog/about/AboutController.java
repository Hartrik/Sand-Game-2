
package cz.hartrik.sg2.app.extension.dialog.about;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Controller ovládající panel informacemi o programu.
 * 
 * @version 2015-02-07
 * @author Patrik Harag
 */
public class AboutController implements Initializable {
    
    private @FXML WebView historyWebView;
    private @FXML WebView infoWebView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPage(infoWebView, "informations.html");
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