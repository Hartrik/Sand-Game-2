package cz.hartrik.common.ui.javafx;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Dialog s výpisem chyby.
 * 
 * @version 2015-03-11
 * @author Patrik Harag
 */
public class ExceptionDialog extends Alert {
    
    private final Throwable exception;

    public ExceptionDialog(Throwable exception) {
        super(AlertType.ERROR);
        this.exception = exception;
        
        init();
    }
    
    private void init() {
        TextArea textArea = new TextArea(stackTrace());
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        textArea.setEditable(false);
        
        VBox vBox = new VBox(textArea);
        vBox.setPadding(new Insets(10));
        VBox.setVgrow(textArea, Priority.ALWAYS);
        
        getDialogPane().setExpandableContent(vBox);
    }
    
    private String stackTrace() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }
    
}