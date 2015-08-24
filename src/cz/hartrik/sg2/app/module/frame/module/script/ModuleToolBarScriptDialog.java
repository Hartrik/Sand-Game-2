package cz.hartrik.sg2.app.module.frame.module.script;

import cz.hartrik.common.reflect.TODO;
import cz.hartrik.jfxeditor.CodeEditor;
import cz.hartrik.jfxeditor.build.CodeMirrorBuilder;
import cz.hartrik.jfxeditor.build.Template;
import cz.hartrik.jfxeditor.codemirror.CMResources;
import cz.hartrik.jfxeditor.dialog.ScriptDialog;
import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.frame.module.ToolBarModule;
import java.util.Map;
import java.util.function.Supplier;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * @version 2015-04-04
 * @author Patrik Harag
 */
@TODO("Zobrazení dialogu může být dostupné jako služba")
public class ModuleToolBarScriptDialog extends ToolBarModule<Frame, FrameController> {
    
    private final String text;

    public ModuleToolBarScriptDialog(String text) {
        super(false);
        this.text = text;
    }

    @Override
    public void setUp(Frame stage, FrameController controller,
            ServiceManager manager) {

        class DialogHolder { MyScriptDialog dialog; }
        final DialogHolder dialogHolder = new DialogHolder();

        ImageView icon = new ImageView(ScriptDialog.PATH_FRAME_ICON);
        icon.setFitHeight(16);
        icon.setFitWidth(16);

        Button button = new Button(text, icon);
        button.setOnAction((e) -> {

            if (dialogHolder.dialog == null)
                dialogHolder.dialog = createDialog(stage, controller);
            
            controller.getSyncTools()
                      .pauseProcessor(dialogHolder.dialog::showAndWait);
        });
        
        controller.getToolBar().getItems().add(button);
    }
    
    private MyScriptDialog createDialog(Frame stage, FrameController controller) {
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
        Map<String, Supplier<?>> map = JSPublicAPI.createBindings(controller);
        String def = JSPublicAPI.defaultCode();

        MyScriptDialog dialog = new MyScriptDialog(cd, stage, map, "js", def);
        dialog.setWidth(700);
        dialog.setMinHeight(500);
        dialog.setTitle("Vlastní script");
        dialog.setPreCode(JSPublicAPI.loadInitCode());
        
        return dialog;
    }

    
}