package cz.hartrik.sg2.app.module;

import cz.hartrik.sg2.app.Application;
import javafx.scene.control.Button;

/**
 * Přidá do toolbaru tlačítko spouštějící požadovanou službu.
 * <b>Nedokáže však určit, zda je daná služby dostupná.</b>
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class ModuleToolBarSimpleButton implements ApplicationModule {

    private final String text;
    private final String service;

    public ModuleToolBarSimpleButton(String text, String service) {
        this.text = text;
        this.service = service;
    }

    @Override
    public void init(Application app) {
        Button button = new Button(text);
        button.setOnAction((e) -> {
            app.getServiceManager().run(service);
        });

        app.getController().getToolBar().getItems().add(button);
    }

}