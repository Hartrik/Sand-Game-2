
package cz.hartrik.sg2.app.sandbox;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.frame.module.io.IOServices;
import java.nio.file.Path;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Vstupní třída.
 *
 * @version 2015-02-20
 * @author Patrik Harag
 */
public class Main extends Application {

    public static final String APP_NAME = "Sand Game 2 (2.02 Beta)";
    public static final String APP_VERSION = "2.02 Beta";
    public static final String ICON = "icon - sg2.png";

    private static Frame frame;

    @Override
    public void start(Stage stage) throws Exception {
        setUserAgentStylesheet(STYLESHEET_MODENA);

        frame = new Frame(MainModules.modules());
        frame.getIcons().add(Resources.image(ICON, Main.class));

        ServiceManager sm = frame.getFrameController().getServiceManager();

        // služby, které by měli být spuštěny před zobrazením
        sm.run(IOServices.SERVICE_FILE_NEW);

        // zobrazení
        frame.show();
    }

    public static void showFileName(Path path) {
        frame.setTitle(Main.APP_NAME +
                (path == null ? "" : " / " + path.getFileName()));
    }

    public static void main(String[] args) {
        launch(args);
    }

    static Frame getFrame() {
        return frame;
    }

}