
package cz.hartrik.sg2.app.module.misc;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.manage.BrushInfo;
import java.text.Normalizer;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Modul přidávající seznam štětců, který lze filtrovat.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class ModuleBrushListFilterable extends ModuleBrushList {

    private String text = "";

    @Override
    public void init(Application app) {
        ListView<Brush> listView = createListView(
                app.getBrushManager(), app.getControls());

        TextField textField = new TextField();
        textField.setPromptText("...");
        textField.setOnKeyReleased((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE)
                textField.setText("");

            text = normalize(textField.getText());
            setItems(listView, app.getBrushManager().getBrushes());
        });

        VBox panel = app.getController().getLeftPanel();
        panel.getChildren().addAll(textField, listView);
        VBox.setVgrow(listView, Priority.ALWAYS);
    }

    @Override
    protected void setItems(ListView<Brush> listView, List<Brush> brushes) {
        ObservableList<Brush> list = FXCollections.observableArrayList(brushes);

        if (text.isEmpty()) {
            listView.setItems(list);

        } else {
            FilteredList<Brush> filtered = new FilteredList<>(list, brush -> {
                BrushInfo info = brush.getInfo();

                return String.valueOf(info.getId()).contains(text)
                        || match(info.getName())
                        || info.getAliases().stream().anyMatch(this::match)
                        || info.getLabels().stream().anyMatch(this::match);
            });

            listView.setItems(filtered);
        }
    }

    private boolean match(String someText) {
        return normalize(someText).contains(text);
    }

    private String normalize(String string) {
        return stripAccents(string.toLowerCase());
    }

    private String stripAccents(String string) {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("\\p{M}", "");
        return string;
    }

}