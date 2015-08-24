package cz.hartrik.sg2.app.module.frame.module.edit;

import cz.hartrik.common.Pair;
import cz.hartrik.sg2.app.module.dialog.size.ChangeSizeDialog;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.Registerable;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.engine.Engine;
import cz.hartrik.sg2.world.ModularWorld;
import javafx.stage.Window;

/**
 * @version 2015-04-07
 * @author Patrik Harag
 */
public class SizeChangeService implements Registerable {
    
    protected final Window window;
    protected final FrameController controller;

    public SizeChangeService(Window window, FrameController frameController) {
        this.window = window;
        this.controller = frameController;
    }
    
    // služby
    
    public void changeSize() {
        final Engine<?, ?> engine = controller.getEngine();
        
        boolean rendererStopped = engine.isRendererStopped();
        boolean processorStopped = engine.isProcessorStopped();
        
        engine.processorStop();
        engine.rendererStop();

        final ModularWorld world = controller.getWorld();
        
        ChangeSizeDialog dialog = new ChangeSizeDialog(
                window, world.getWidth(), world.getHeight());
        
        dialog.showAndWait();
        Pair<Integer, Integer> size = dialog.getSize();
        
        if (size != null && (size.getFirst() != world.getWidth()
                || size.getSecond() != world.getHeight())) {
            
            controller.setUpCanvas(
                    world.getTools().resize(size.getFirst(), size.getSecond()));
            
            if (!processorStopped) controller.getEngine().processorStart();
            
        } else {
            if (!rendererStopped) engine.rendererStart();
            if (!processorStopped) engine.processorStart();
        }
    }
    
    // registrační metoda
    
    @Override
    public void register(ServiceManager manager) {
        manager.register(EditServices.SERVICE_CHANGE_SIZE, this::changeSize);
    }
    
}
