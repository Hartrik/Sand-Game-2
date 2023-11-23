
package cz.hartrik.sg2.app.extension.dialog.script;

import cz.hartrik.common.io.ClipboardUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.util.Pair;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

/**
 * Kontroler FXML dokumentu.
 *
 * @version 2023-11-23
 * @author Patrik Harag
 */
public class ScriptDialogController implements Initializable {

    public String defaultCode = "";
    public String defaultEngine = "js";
    public Map<String, Supplier<?>> bindings;
    public String preCode = "";
    
    @FXML protected TextArea codeEditor;
    @FXML protected TextArea outputArea;
    @FXML protected ComboBox<ScriptEngineFactory> engineComboBox;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            codeEditor.setText(defaultCode);

            ScriptEngineManager manager = new ScriptEngineManager();
            EngineCellFactory cellFactory = new EngineCellFactory();
            engineComboBox.setButtonCell(cellFactory.call(null));
            engineComboBox.setCellFactory(cellFactory);
            engineComboBox.getItems().addAll(manager.getEngineFactories());
            
            List<ScriptEngineFactory> factories = manager.getEngineFactories();
            ScriptEngineFactory factory = getFactory(defaultEngine, factories);
            if (factory == null)
                factory = getFactory("js", factories);
            
            engineComboBox.getSelectionModel().select(factory);
        });
    }
    
    protected static ScriptEngineFactory getFactory(
            String name, List<ScriptEngineFactory> factories) {
        
        for (ScriptEngineFactory factory : factories)
            if (factory.getNames().contains(name))
                return factory;
        
        return null;
    }
    
    @FXML
    public void eval() {
        ScriptEngine engine = engineComboBox.getSelectionModel()
                .getSelectedItem().getScriptEngine();
        
        if (bindings != null) {
            Map<String, Object> collect = bindings.entrySet().stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue().get()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
            
            engine.getBindings(ScriptContext.ENGINE_SCOPE).putAll(collect);
        }
        
        StringWriter outSW = new StringWriter();
        PrintWriter outPW  = new PrintWriter(outSW);
        engine.getContext().setWriter(outPW);
        
        try {
            if (!preCode.isEmpty())
                engine.eval(preCode);
            
            engine.eval(codeEditor.getText());
            
        } catch (Exception ex) {
            outputArea.setText(ex.toString());
            return;
        }
        
        outputArea.setText(outSW.getBuffer().toString());
    }
    
    @FXML
    public void copy() {
        ClipboardUtil.copy(outputArea.getText());
    }
    
}