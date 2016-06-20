package cz.hartrik.sg2.app.module.frame.module.misc;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.StageModule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.brush.manage.ObservedBrushManager;
import java.util.List;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @version 2015-04-06
 * @author Patrik Harag
 */
public class ModuleBrushList implements StageModule<Frame, FrameController> {

    @Override
    public void init(Frame stage, FrameController controller,
            ServiceManager manager) {

        final VBox panel = controller.getLeftPanel();
        final ListView<Brush> listView = createListView(controller);

        panel.getChildren().add(listView);
        VBox.setVgrow(listView, Priority.ALWAYS);
    }

    protected ListView<Brush> createListView(FrameController controller) {
        final BrushManager brushManager = controller.getBrushManager();
        final Controls controls = controller.getControls();

        final ListView<Brush> listView = new ListView<>();

        setItems(listView, brushManager.getBrushes());
        listView.setCellFactory(new BrushCellFactory(controls));
        listView.getSelectionModel().select(controls.getPrimaryBrush());
        listView.setFocusTraversable(false);

        assert brushManager instanceof ObservedBrushManager;
        ((ObservedBrushManager) brushManager).addListener(
                () -> setItems(listView, brushManager.getBrushes()));

        return listView;
    }

    protected void setItems(ListView<Brush> listView, List<Brush> brushes) {
        listView.getItems().setAll(brushes);
    }

}
