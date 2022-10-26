
package cz.hartrik.sg2.app.module.script;

import cz.hartrik.common.ui.javafx.ExceptionDialog;
import cz.hartrik.common.ui.javafx.OutputDialog;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.Strings;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.util.Pair;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @version 2017-07-05
 * @author Patrik Harag
 */
public class JSExecuter {

    private final Application application;
    private final JSPublicAPI api;

    public JSExecuter(Application application) {
        this.application = application;
        this.api = new JSPublicAPI(application);
    }

    public void eval(Path script) {
        application.getSyncTools().pauseProcessor(() -> evalIn(script));
    }

    private void evalIn(Path script) {
        final ScriptEngineManager manager = new ScriptEngineManager();
        final ScriptEngine engine = manager.getEngineByExtension("js");

        Map<String, Object> collect = api.getBindings().entrySet().stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue().get()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        engine.getBindings(ScriptContext.ENGINE_SCOPE).putAll(collect);

        StringWriter out = new StringWriter();
        engine.getContext().setWriter(new PrintWriter(out));

        try {
            engine.eval(api.loadInitCode());
            engine.eval(load(script));

        } catch (Exception ex) {
            ExceptionDialog dialog = new ExceptionDialog(ex);
            dialog.initOwner(application.getStage());
            dialog.setTitle(Strings.get("module.script.err-exe.title"));
            dialog.setHeaderText(Strings.get("module.script.err-exe.header"));
            dialog.setContentText(Strings.get("module.script.err-exe.content"));
            dialog.showAndWait();
        }

        final String outString = out.getBuffer().toString();
        if (!outString.isEmpty()) {
            OutputDialog dialog = new OutputDialog(outString);
            dialog.initOwner(application.getStage());
            dialog.setTitle(Strings.get("module.script.out.title"));
            dialog.setHeaderText(Strings.get("module.script.out.header", script.getFileName()));
            dialog.setContentText(Strings.get("module.script.out.content"));
            dialog.showAndWait();
        }
    }

    private String load(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

}