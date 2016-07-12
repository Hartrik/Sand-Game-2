package cz.hartrik.sg2.app.module.edit;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.ApplicationModule;
import cz.hartrik.sg2.app.service.Require;
import javafx.scene.control.Button;

/**
 * Modul přidávající do toolbaru tlačítko vyvolávající dialog pro změnu
 * velikosti plátna.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@Require(SizeChangeService.class)
public class ModuleToolBarSizeChange implements ApplicationModule {

    @Override
    public void init(Application app) {

        Button button = new Button("Změnit rozměry");
        button.setOnAction((e) -> {
            app.getServiceManager().run(SizeChangeService.SERVICE_CHANGE_SIZE);
        });

        app.getController().getToolBar().getItems().add(button);
    }

}