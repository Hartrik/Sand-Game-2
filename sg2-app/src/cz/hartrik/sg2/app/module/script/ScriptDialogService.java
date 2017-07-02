package cz.hartrik.sg2.app.module.script;

import cz.hartrik.jfxeditor.CodeEditor;
import cz.hartrik.jfxeditor.build.CodeMirrorBuilder;
import cz.hartrik.jfxeditor.build.Template;
import cz.hartrik.jfxeditor.codemirror.CMResources;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.Frame;
import cz.hartrik.sg2.app.service.Service;
import cz.hartrik.sg2.app.service.ServiceProvider;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Poskytuje službu scriptovacího dialogu.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
@ServiceProvider
public class ScriptDialogService {

    public static final String SERVICE_SHOW_SCRIPT_DIALOG = "script-dialog";

    private MyScriptDialog dialog;

    @Service(SERVICE_SHOW_SCRIPT_DIALOG)
    public void showScriptDialog(Application app) {
        if (dialog == null)
            dialog = createDialog(app.getStage(), app);

        app.getSyncTools().pauseProcessor(dialog::showAndWait);
    }

    private MyScriptDialog createDialog(Frame owner, Application app) {
        String template = Template.load()
                .replace("<html>",
                        "<html style=\"background-color: #E0E0E0 \">");

        String fullTemplate = new CodeMirrorBuilder(template)
                .addTheme(CMResources.themeDefault())
                .addTheme(CMResources.themeEclipse())
                .addScript(CMResources.scriptBase())
                .addScript(CMResources.modeJavaScript())
                .addScript(CMResources.addonActiveLine())
                .addScript(CMResources.addonMatchBrackets())
                .selectMode("text/javascript")
                .selectTheme("eclipse")
                .build();

        CodeEditor cd = new CodeEditor("text", fullTemplate);
        Map<String, Supplier<?>> map = JSPublicAPI.createBindings(app);
        String def = JSPublicAPI.defaultCode();

        MyScriptDialog dialog = new MyScriptDialog(cd, owner, map, "js", def);
        dialog.setWidth(700);
        dialog.setMinHeight(500);
        dialog.setTitle("Vlastní script");
        dialog.setPreCode(JSPublicAPI.loadInitCode());

        return dialog;
    }

}