package cz.hartrik.sg2.app.module.frame.module.about;

import cz.hartrik.sg2.app.module.dialog.about.AboutDialog;
import cz.hartrik.sg2.app.module.frame.Application;
import cz.hartrik.sg2.app.module.frame.service.Service;
import cz.hartrik.sg2.app.module.frame.service.ServiceProvider;

/**
 * Poskytuje službu na vyvolání okna s informacemi o aplikaci.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@ServiceProvider
public class AboutServices {

    public static final String SERVICE_ABOUT_DIALOG = "about";

    private AboutDialog dialog;

    // služby

    @Service(SERVICE_ABOUT_DIALOG)
    public void aboutDialog(Application app) {
        app.getSyncTools().pauseBothLazy(() -> {
            synchronized (this) {
                if (dialog == null)
                    dialog = new AboutDialog(app.getStage());

                dialog.showAndWait();
            }
        });
    }

}
