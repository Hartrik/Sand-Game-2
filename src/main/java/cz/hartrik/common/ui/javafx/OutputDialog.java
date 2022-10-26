package cz.hartrik.common.ui.javafx;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Dialog s textovým polem.
 * 
 * @version 2015-03-14
 * @author Patrik Harag
 */
public class OutputDialog extends Alert {
    
    private final String text;

    public OutputDialog(String text) {
        super(Alert.AlertType.NONE);
        this.text = text;
        
        init();
    }
    
    private void init() {
        TextArea textArea = new TextArea(text);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        textArea.setEditable(false);
        
        VBox vBox = new VBox(textArea);
        vBox.setPadding(new Insets(10));
        VBox.setVgrow(textArea, Priority.ALWAYS);
        
        getDialogPane().setExpandableContent(vBox);
        getDialogPane().setExpanded(true);
        
        getButtonTypes().add(ButtonType.OK);
    }
    
}
