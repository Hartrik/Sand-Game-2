package cz.hartrik.sg2.app.module.frame.module.tools;

import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.script.ToolFactory;
import cz.hartrik.sg2.brush.Controls;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @version 2016-06-28
 * @author Patrik Harag
 */
public class PanelToolRectangle extends PanelTool {

    private Spinner<Integer> sWidth;
    private Spinner<Integer> sHeight;

    public PanelToolRectangle(int min, int max, int def, FrameController controller) {
        super(min, max, def, controller);
        init();
    }

    private void init() {
        final Label lWidth = new Label("Šířka");
        final Label lHeight = new Label("Výška");

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
        final Controls controls = controller.getControls();
        final ToolFactory factory = ToolFactory.getInstance();
        controls.setPrimaryTool(factory.centeredRectangle(w, h));
        controls.setSecondaryTool(factory.centeredRectangle(w, h));
    }

}