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
 * @version 2015-03-29
 * @author Patrik Harag
 */
public class PanelToolTriangle extends PanelTool {

    private Spinner<Integer> sFirst;
    private Spinner<Integer> sSecond;
    private Spinner<Integer> sThird;
    
    public PanelToolTriangle(int min, int max, int def, FrameController controller) {
        super(min, max, def, controller);
        init();
    }
    
    private void init() {
        final Label lFirst = new Label("Délka a");
        final Label lSecond = new Label("Délka b");
        final Label lThird = new Label("Délka c");
        
        lFirst.setPrefWidth(70);
        lSecond.setPrefWidth(70);
        lThird.setPrefWidth(70);
        
        sFirst = new Spinner<>(min, max, def);
        sFirst.setEditable(true);
        sFirst.getValueFactory().setConverter(new IntegerConverter());
        
        sSecond = new Spinner<>(min, max, def);
        sSecond.setEditable(true);
        sSecond.getValueFactory().setConverter(new IntegerConverter());
        
        sThird = new Spinner<>(min, max, def);
        sThird.setEditable(true);
        sThird.getValueFactory().setConverter(new IntegerConverter());
        
        ChangeListener<Integer> listener = (ob, o, n) -> updateTool();
        
        sFirst.valueProperty().addListener(listener);
        sSecond.valueProperty().addListener(listener);
        sThird.valueProperty().addListener(listener);
        
        final HBox bFirst  = new HBox(lFirst,  sFirst);
        final HBox bSecond = new HBox(lSecond, sSecond);
        final HBox bThird  = new HBox(lThird,  sThird);
        
        bFirst.setAlignment(Pos.CENTER);
        bSecond.setAlignment(Pos.CENTER);
        bThird.setAlignment(Pos.CENTER);
        
        this.pane = new VBox(10, bFirst, bSecond, bThird);
    }

    @Override
    public void updateTool() {
        updateTool(sFirst.getValue(), sSecond.getValue(), sThird.getValue());
    }
    
    public void updateTool(double a, double b, double c) {
        final Controls controls = controller.getControls();
        final ToolFactory factory = ToolFactory.getInstance();
        controls.setPrimaryTool(factory.centeredTriangle(a, b, c));
        controls.setSecondaryTool(factory.centeredTriangle(a, b, c));
    }
    
}
