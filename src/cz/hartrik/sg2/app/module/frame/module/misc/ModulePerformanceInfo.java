package cz.hartrik.sg2.app.module.frame.module.misc;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.ProcessingState;
import cz.hartrik.sg2.app.module.frame.StageModule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.engine.EngineListenerDef;
import cz.hartrik.sg2.engine.JFXEngine;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;

/**
 * @version 2016-06-26
 * @author Patrik Harag
 */
public class ModulePerformanceInfo implements StageModule<Frame, FrameController> {

    private volatile boolean processorStopped = true;

    @Override
    public void init(Frame stage, FrameController controller,
            ServiceManager manager) {

        final Label lSizes  = new Label("0");
        final Label lFPS    = new Label("0");
        final Label lCycles = new Label("0");
        final Label lChunks = new Label("0");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(2);

        grid.addRow(0, new Label("Rozměry"),          lSizes);
        grid.addRow(1, new Label("FPS"),              lFPS);
        grid.addRow(2, new Label("Cyklů za sekundu"), lCycles);
        grid.addRow(3, new Label("Aktivní chunky"),   lChunks);

        controller.addOnSetUp(() -> {
            update(controller, lSizes, lFPS, lCycles, lChunks);
        });

        controller.addOnEngineStateChanged((state) -> {
            processorStopped = (state == ProcessingState.STOPPED);
        });

        controller.getLeftPanel().getChildren().addAll(grid, new Separator());
    }

    // musí být zavoláno při každé změně plátna
    private void update(FrameController controller,
            Label lSizes, Label lFPS, Label lCycles, Label lChunks) {

        final JFXEngine<?> engine = controller.getEngine();
        final int height = controller.getWorld().getHeight();
        final int width  = controller.getWorld().getWidth();
        final String chunkStr = " / " + (controller.getWorld().getChunkCount());

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