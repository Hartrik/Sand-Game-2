package cz.hartrik.sg2.app.module.misc;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.ApplicationModule;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.brush.manage.ObservedBrushManager;
import java.util.List;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Modul přidávající seznam štětců.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class ModuleBrushList implements ApplicationModule {

    @Override
    public void init(Application app) {
        ListView<Brush> listView = createListView(
                app.getBrushManager(), app.getControls());

        VBox panel = app.getController().getLeftPanel();
        panel.getChildren().add(listView);

        VBox.setVgrow(listView, Priority.ALWAYS);
    }

    protected ListView<Brush> createListView(
            BrushManager brushManager, Controls controls) {

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