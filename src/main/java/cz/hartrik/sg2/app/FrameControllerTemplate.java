package cz.hartrik.sg2.app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Předek pro controller, který definuje základní prvky UI a provádí některá
 * jejich nastavení, jako je nastavení velikosti...
 *
 * @version 2017-08-07
 * @author Patrik Harag
 */
public abstract class FrameControllerTemplate implements Initializable {

    @FXML protected StackPane rootPane;

     @FXML protected MenuBar menuBar;
     @FXML protected ToolBar toolBar;

     @FXML protected SplitPane splitPane;
      @FXML protected VBox leftPanel;
      @FXML protected ScrollPane scrollPane;

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    public VBox getLeftPanel() {
        return leftPanel;
    }

    public StackPane getRootPane() {
        return rootPane;
    }

    // Initializable

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        splitPane.setDividerPosition(0, 0d);
    }

    // ostatní

    public Stage getStage() {
        return (Stage) menuBar.getScene().getWindow();
    }

}