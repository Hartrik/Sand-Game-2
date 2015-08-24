package cz.hartrik.sg2.app.module.frame.module.script;

import cz.hartrik.common.ui.javafx.ExceptionDialog;
import cz.hartrik.common.ui.javafx.OutputDialog;
import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javafx.util.Pair;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @version 2015-03-27
 * @author Patrik Harag
 */
public class JSExecuter {
    
    private final FrameController controller;
    private final Map<String, Supplier<?>> bindings;
    private final Frame stage;

    public JSExecuter(Frame stage, FrameController controller) {
        this.controller = controller;
        this.bindings = JSPublicAPI.createBindings(controller);
        this.stage = stage;
    }
    
    public void eval(Path script) {
        controller.getSyncTools().pauseProcessor(() -> evalIn(script));
    }
    
    private void evalIn(Path script) {
        final ScriptEngineManager manager = new ScriptEngineManager();
        final ScriptEngine engine = manager.getEngineByExtension("js");

        Map<String, Object> collect = bindings.entrySet().stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue().get()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        engine.getBindings(ScriptContext.ENGINE_SCOPE).putAll(collect);
        
        StringWriter out = new StringWriter();
        engine.getContext().setWriter(new PrintWriter(out));
        
        try {
            engine.eval(JSPublicAPI.loadInitCode());
            engine.eval(load(script));
            
        } catch (Exception ex) {
            ExceptionDialog dialog = new ExceptionDialog(ex);
            dialog.initOwner(stage);
            dialog.setTitle("Chyba");
            dialog.setHeaderText("Došlo k chybě při spouštění scriptu");
            dialog.setContentText("Podrobnosti:");
            dialog.showAndWait();
        }
        
        final String outString = out.getBuffer().toString();
        if (!outString.isEmpty()) {
            OutputDialog dialog = new OutputDialog(outString);
            dialog.initOwner(stage);
            dialog.setTitle("Výstup");
            dialog.setHeaderText("Script \"" + script.getFileName()
                    + "\" za sebou zanechal textový výstup");
            dialog.setContentText("Výstup:");
            dialog.showAndWait();
        }
    }
    
    private String load(Path path) throws IOException {
        
        // return Files.lines(path).collect(Collectors.joining("\n"));
        
        // - tato metoda zřejmě neuzavírá proud, a proto soubor až do vypnutí
        //   aplikace nelze ve Windows otevřít
        
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
    
}