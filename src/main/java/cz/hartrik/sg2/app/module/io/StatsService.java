
package cz.hartrik.sg2.app.module.io;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.extension.dialog.stats.StatsDialog;
import cz.hartrik.sg2.app.service.Service;
import cz.hartrik.sg2.app.service.ServiceProvider;
import cz.hartrik.sg2.world.ElementArea;

/**
 * Poskytuje zobrazení okna se statistikami plátna jako službu.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@ServiceProvider
public class StatsService {

    public static final String SERVICE_STATS = "stats";

    @Service(SERVICE_STATS)
    public void showStats(Application app) {
        app.getSyncTools().pauseBothLazy(() -> {
            final ElementArea area = app.getWorld();
            final StatsDialog dialog = new StatsDialog(app.getStage(), area);
            dialog.showAndWait();
        });
    }

}