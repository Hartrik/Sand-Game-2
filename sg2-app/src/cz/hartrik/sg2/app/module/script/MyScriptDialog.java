
package cz.hartrik.sg2.app.module.script;

import cz.hartrik.jfxeditor.CodeEditor;
import cz.hartrik.jfxeditor.dialog.ScriptDialog;
import cz.hartrik.jfxeditor.dialog.ScriptDialogController;
import java.util.Map;
import java.util.function.Supplier;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * @version 2015-04-04
 * @author Patrik Harag
 */
public class MyScriptDialog extends ScriptDialog {

    public MyScriptDialog(
            CodeEditor codeEditor, Window owner,
            Map<String, Supplier<?>> bindings, String defaultEngine,
            String defaultCode) {
        
        super(codeEditor, owner, bindings, defaultEngine, defaultCode);
        initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected ScriptDialogController getController() {
        return super.getController();
    }
    
    public void setPreCode(String code) {
        controller.preCode = code;
    }
    
}