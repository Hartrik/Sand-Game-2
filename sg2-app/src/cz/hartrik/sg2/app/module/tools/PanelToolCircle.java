package cz.hartrik.sg2.app.module.tools;

import cz.hartrik.sg2.app.module.script.ToolFactory;
import cz.hartrik.sg2.brush.Controls;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;

/**
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class PanelToolCircle extends PanelTool {

    private final Controls controls;

    private Spinner<Integer> sRadius;

    public PanelToolCircle(int min, int max, int def, Controls controls) {
        super(min, max, def);
        this.controls = controls;
        init();
    }

    private void init() {
        final Label lRadius = new Label("Poloměr");
        lRadius.setPrefWidth(70);

        sRadius = createSpinner(min, max, def);
        sRadius.valueProperty().addListener((ob, o, n) -> updateTool(n));

        HBox bRadius = new HBox(lRadius, sRadius);
        bRadius.setAlignment(Pos.CENTER);
        this.pane = bRadius;
    }

    @Override
    public void updateTool() {
        updateTool(sRadius.getValue());
    }

    public void updateTool(int r) {
        ToolFactory factory = ToolFactory.getInstance();
        controls.setPrimaryTool(factory.centeredCircle(r));
        controls.setSecondaryTool(factory.centeredCircle(r));
    }

}
