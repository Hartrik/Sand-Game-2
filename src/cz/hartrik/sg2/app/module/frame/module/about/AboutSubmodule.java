package cz.hartrik.sg2.app.module.frame.module.about;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.module.dialog.about.AboutDialog;
import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

/**
 * @version 2015-02-07
 * @author Patrik Harag
 */
public class AboutSubmodule extends MenuSubmodule<Frame, FrameController> {

    public AboutSubmodule(boolean register) {
        super(register);
    }

    @Override
    public void register(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        new AboutServices(stage, controller).register(manager);
    }

    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        Image icon = Resources.image(AboutDialog.ICON, AboutDialog.class);
        
        MenuItem item = new MenuItem("O programu");
        item.setOnAction((e) -> manager.run(AboutServices.SERVICE_ABOUT_DIALOG));
        item.setAccelerator(KeyCombination.keyCombination("F1"));
        item.setGraphic(new ImageView(icon));
        
        return new MenuItem[] { item };
    }

}