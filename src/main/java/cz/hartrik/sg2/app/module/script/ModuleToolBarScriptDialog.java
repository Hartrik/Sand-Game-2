
package cz.hartrik.sg2.app.module.script;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.extension.dialog.script.ScriptDialog;
import cz.hartrik.sg2.app.module.ApplicationModule;
import cz.hartrik.sg2.app.service.Require;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Modul přidá do toolbaru tlačítko vyvolávající scriptovací dialog.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
@Require(ScriptDialogService.class)
public class ModuleToolBarScriptDialog implements ApplicationModule {

    private final String text;

    public ModuleToolBarScriptDialog(String text) {
        this.text = text;
    }

    @Override
    public void init(Application app) {
        ImageView icon = new ImageView(ScriptDialog.PATH_FRAME_ICON);
        icon.setFitHeight(16);
        icon.setFitWidth(16);

        Button button = new Button(text, icon);
        button.setOnAction((e) -> {
            app.getServiceManager().run(ScriptDialogService.SERVICE_SHOW_SCRIPT_DIALOG);
        });

        app.getController().getToolBar().getItems().add(button);
    }

}