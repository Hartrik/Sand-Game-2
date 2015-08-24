package cz.hartrik.sg2.app.module.frame.module.tools;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

/**
 *
 * @version 2015-03-28
 * @author Patrik Harag
 */
public class ShapesSubmodule extends MenuSubmodule<Frame, FrameController> {

    public ShapesSubmodule() {
        super(false);
    }

    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {

        final VBox content = new VBox();
        
        PanelTool rp = new PanelToolRectangle(1, 150, 10, controller);
        PanelTool rc = new PanelToolCircle(1, 75, 5, controller);
        PanelTool rt = new PanelToolTriangle(1, 75, 10, controller);
        
        final String RECT = "Obdélník";
        final String CIRCLE = "Kruh";
        final String TRIANGLE = "Trojúhelík";
        
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().setAll(RECT, CIRCLE, TRIANGLE);
        comboBox.valueProperty().addListener((ob, o, n) -> {
            content.getChildren().clear();
            
            PanelTool tool = n.equals(RECT) ? rp
                    : (n.equals(TRIANGLE) ? rt : rc);
            
            content.getChildren().add(tool.getPane());
            tool.updateTool();
        });
        
        comboBox.getSelectionModel().selectFirst();
        
        final VBox box = new VBox(10, comboBox, content);
        box.setPadding(new Insets(5, 16, 5, 16));
        
        return new MenuItem[] { new CustomMenuItem(box, false) };
    }
    
}