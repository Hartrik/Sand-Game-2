
package cz.hartrik.sg2.app.module.misc;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.FrameController;
import cz.hartrik.sg2.app.ProcessingState;
import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.app.module.ApplicationModule;
import cz.hartrik.sg2.engine.EngineListenerDef;
import cz.hartrik.sg2.engine.JFXEngine;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;

/**
 * Modul zobrazující informace o enginu a rozměrech plátna.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public class ModulePerformanceInfo implements ApplicationModule {

    private volatile boolean processorStopped = true;

    @Override
    public void init(Application application) {
        FrameController controller = application.getController();

        final Label lSizes  = new Label("0");
        final Label lFPS    = new Label("0");
        final Label lCycles = new Label("0");
        final Label lChunks = new Label("0");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(2);

        grid.addRow(0, new Label(Strings.get("module.misc.perf.sizes")), lSizes);
        grid.addRow(1, new Label(Strings.get("module.misc.perf.fps")), lFPS);
        grid.addRow(2, new Label(Strings.get("module.misc.perf.cycles")), lCycles);
        grid.addRow(3, new Label(Strings.get("module.misc.perf.chunks")), lChunks);

        controller.addOnSetUp(() -> {
            // musí být zavoláno při každé změně plátna
            update(application, lSizes, lFPS, lCycles, lChunks);
        });

        controller.addOnEngineStateChanged((state) -> {
            processorStopped = (state == ProcessingState.STOPPED);
        });

        controller.getLeftPanel().getChildren().addAll(grid, new Separator());
    }

    private void update(Application app,
            Label lSizes, Label lFPS, Label lCycles, Label lChunks) {

        final JFXEngine<?> engine = app.getEngine();
        final int height = app.getWorld().getHeight();
        final int width  = app.getWorld().getWidth();
        final String chunkStr = " / " + (app.getWorld().getChunkCount());

        lSizes.setText(width + " x " + height + " (" + (width * height) + ")");

        engine.setListener(new EngineListenerDef() {
            private long lastUpdate;

            @Override
            public void rendererCycleEnd() {
                long now = System.currentTimeMillis();
                if (now - lastUpdate > 500)
                    lastUpdate = now;
                else return;

                Platform.runLater(() -> {
                    lFPS.setText(engine.getCurrentFPS() + "");

                    if (processorStopped) {
                        lChunks.setText("n/a");
                        lCycles.setText("n/a");
                    } else {
                        lChunks.setText(engine.getUpdatedChunksCount() + chunkStr);
                        lCycles.setText(engine.getCurrentCycles() + "");
                    }
                });
            }
        });
    }

}