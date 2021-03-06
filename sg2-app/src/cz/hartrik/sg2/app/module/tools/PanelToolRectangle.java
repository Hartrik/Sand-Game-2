
package cz.hartrik.sg2.app.module.tools;

import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.app.module.script.ToolFactory;
import cz.hartrik.sg2.brush.Controls;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class PanelToolRectangle extends PanelTool {

    private final Controls controls;

    private Spinner<Integer> sWidth;
    private Spinner<Integer> sHeight;

    public PanelToolRectangle(int min, int max, int def, Controls controls) {
        super(min, max, def);
        this.controls = controls;
        init();
    }

    private void init() {
        final Label lWidth = new Label(Strings.get("module.tools.rect.w"));
        final Label lHeight = new Label(Strings.get("module.tools.rect.h"));

        lWidth.setPrefWidth(70);
        lHeight.setPrefWidth(70);

        sWidth = createSpinner(min, max, def);
        sHeight = createSpinner(min, max, def);

        ChangeListener<Integer> listener = (ob, o, n) -> updateTool();

        sWidth.valueProperty().addListener(listener);
        sHeight.valueProperty().addListener(listener);

        final HBox bWidth = new HBox(lWidth, sWidth);
        final HBox bHeight = new HBox(lHeight, sHeight);

        bWidth.setAlignment(Pos.CENTER);
        bHeight.setAlignment(Pos.CENTER);

        this.pane = new VBox(10, bWidth, bHeight);
    }

    @Override
    public void updateTool() {
        updateTool(sWidth.getValue(), sHeight.getValue());
    }

    public void updateTool(int w, int h) {
        final ToolFactory factory = ToolFactory.getInstance();
        controls.setPrimaryTool(factory.centeredRectangle(w, h));
        controls.setSecondaryTool(factory.centeredRectangle(w, h));
    }

}