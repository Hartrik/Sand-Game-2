package cz.hartrik.sg2.app.module.frame.module.misc;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
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
 * @version 2015-04-06
 * @author Patrik Harag
 */
public class ModuleBrushListFilterable extends ModuleBrushList {

    private String text = "";
    
    @Override
    public void init(Frame stage, FrameController controller, ServiceManager manager) {
        final VBox panel = controller.getLeftPanel();
        final ListView<Brush> listView = createListView(controller);
        
        TextField textField = new TextField();
        textField.setPromptText("Filtrovat...");
        textField.setOnKeyReleased((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE)
                textField.setText("");
            
            text = normalize(textField.getText());
            setItems(listView, controller.getBrushManager().getBrushes());
        });
        
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