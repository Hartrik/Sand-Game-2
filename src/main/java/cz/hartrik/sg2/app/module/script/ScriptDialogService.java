
package cz.hartrik.sg2.app.module.script;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.Frame;
import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.app.extension.dialog.script.ScriptDialog;
import cz.hartrik.sg2.app.service.Service;
import cz.hartrik.sg2.app.service.ServiceProvider;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Poskytuje službu scriptovacího dialogu.
 *
 * @version 2023-11-23
 * @author Patrik Harag
 */
@ServiceProvider
public class ScriptDialogService {

    public static final String SERVICE_SHOW_SCRIPT_DIALOG = "script-dialog";

    private ScriptDialog dialog;

    @Service(SERVICE_SHOW_SCRIPT_DIALOG)
    public void showScriptDialog(Application app) {
        if (dialog == null)
            dialog = createDialog(app.getStage(), app);

        app.getSyncTools().pauseProcessor(dialog::showAndWait);
    }

    private ScriptDialog createDialog(Frame owner, Application app) {
        JSPublicAPI api = new JSPublicAPI(app);
        Map<String, Supplier<?>> map = api.getBindings();
        String def = api.defaultCode();

        ScriptDialog dialog = new ScriptDialog(owner, map, "js", def);
        dialog.setWidth(700);
        dialog.setMinHeight(500);
        dialog.setTitle(Strings.get("module.script.scripting-dialog"));
        dialog.setPreCode(api.loadInitCode());

        return dialog;
    }

}