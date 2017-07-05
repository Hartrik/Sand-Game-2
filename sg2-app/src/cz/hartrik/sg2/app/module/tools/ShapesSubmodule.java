
package cz.hartrik.sg2.app.module.tools;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

/**
 * Sub-modul do menu, přidávající panel s výběrem nástroje.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class ShapesSubmodule implements MenuSubmodule {

    @Override
    public MenuItem[] createMenuItems(Application app) {
        final VBox content = new VBox();

        PanelTool rp = new PanelToolRectangle(1, 150, 10, app.getControls());
        PanelTool rc = new PanelToolCircle(1, 75, 5, app.getControls());
        PanelTool rt = new PanelToolTriangle(1, 75, 10, app.getControls());

        final String RECT = Strings.get("module.tools.rect");
        final String CIRCLE = Strings.get("module.tools.circle");
        final String TRIANGLE = Strings.get("module.tools.triangle");

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