package cz.hartrik.sg2.app.module.frame.module.misc;

import cz.hartrik.common.reflect.TODO;
import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.StageModule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.engine.EngineListenerDef;
import cz.hartrik.sg2.engine.JFXEngine;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;

/**
 * @version 2015-04-06
 * @author Patrik Harag
 */
@TODO("projevit zastavení procesoru a rendereru")
public class ModulePerformanceInfo implements StageModule<Frame, FrameController> {

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
        
        controller.addOnSetUp(
                () -> update(controller, lSizes, lFPS, lCycles, lChunks));
        
        controller.getLeftPanel().getChildren().addAll(grid, new Separator());
    }
    
    // musí být zavoláno při každé změně ElementArea
    private void update(FrameController controller,
            Label lSizes, Label lFPS, Label lCycles, Label lChunks) {
        
        final JFXEngine<?> engine = controller.getEngine();
        final int height = controller.getWorld().getHeight();
        final int width  = controller.getWorld().getWidth();
        final String chunkStr = " ze " + (width * height / 100 / 100);
        
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
                    lChunks.setText(engine.getUpdatedChunksCount() + chunkStr);
                    lCycles.setText(engine.getCurrentCycles() + "");
                    lFPS.setText(engine.getCurrentFPS() + "");
                });
            }
        });
    }
    
}