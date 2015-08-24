package cz.hartrik.sg2.app.module.frame;

import cz.hartrik.sg2.app.module.frame.module.IContainsMenu;
import cz.hartrik.sg2.app.module.frame.module.IContainsToolBar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Předek pro controller, který definuje základní prvky UI a provádí některá
 * jejich nastavení, jako je nastavení velikosti...
 *
 * @version 2015-04-06
 * @author Patrik Harag
 */
public abstract class FrameControllerTemplate
        implements Initializable, IContainsMenu, IContainsToolBar {
    
    @FXML protected MenuBar menuBar;
    @FXML protected ToolBar toolBar;
    
    @FXML protected SplitPane splitPane;
     @FXML protected VBox leftPanel;
     @FXML protected ScrollPane scrollPane;
    
    // IContainsMenu, IContainsToolBar
    
    @Override
    public MenuBar getMenuBar() {
        return menuBar;
    }
    
    @Override
    public ToolBar getToolBar() {
        return toolBar;
    }
    
    // další přístupové metody

    public VBox getLeftPanel() {
        return leftPanel;
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