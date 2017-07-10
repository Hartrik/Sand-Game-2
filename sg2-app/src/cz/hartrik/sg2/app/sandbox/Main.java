
package cz.hartrik.sg2.app.sandbox;

import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.ApplicationBuilder;
import cz.hartrik.sg2.app.Frame;
import cz.hartrik.sg2.app.module.io.FileServices;
import cz.hartrik.sg2.app.service.ServiceManager;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import javafx.stage.Stage;

/**
 * Vstupní třída.
 *
 * @version 2017-07-10
 * @author Patrik Harag
 */
public class Main extends javafx.application.Application {

    public static final String APP_NAME = "Sand Game 2";
    public static final String APP_VERSION = "2.03 Beta";
    public static final String APP_VERSION_DATE = "2016-07-03";
    public static final String APP_TITLE = APP_NAME + " (" + APP_VERSION + ")";
    public static final String APP_AUTHOR = "© 2016 Patrik Harag\npatrik.harag@gmail.com";
    public static final String APP_WEB = "https://github.com/Hartrik/Sand-Game-2";

    public static final String ICON = "icon - sg2.png";

    private static Frame frame;

    @Override
    public void start(Stage stage) throws Exception {
        processParameters(getParameters());

        setUserAgentStylesheet(STYLESHEET_MODENA);

        Application app = ApplicationBuilder.build(MainModules.modules());

        frame = app.getStage();
        frame.getIcons().add(Resources.image(ICON, Main.class));

        ServiceManager sm = app.getServiceManager();

        // služby, které by měli být spuštěny před zobrazením
        sm.run(FileServices.SERVICE_FILE_NEW);

        // zobrazení
        frame.show();
    }

    private void processParameters(Parameters parameters) {
        Map<String, String> map = parameters.getNamed();

        String locale = map.getOrDefault("locale", null);
        if (locale != null) {
            try {
                Locale.setDefault(Locale.forLanguageTag(locale));
            } catch (Exception e) {
                System.err.printf("Cannot set locale to '%s'%n", locale);
            }
        }
    }

    static void showFileName(Path path) {
        frame.setTitle(Main.APP_TITLE +
                (path == null ? "" : " / " + path.getFileName()));
    }

    static Frame getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        launch(args);
    }

}