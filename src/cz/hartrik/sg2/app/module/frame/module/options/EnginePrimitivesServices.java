package cz.hartrik.sg2.app.module.frame.module.options;

import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.Registerable;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.engine.Engine;

import static cz.hartrik.sg2.app.module.frame.module.options.OptionsServices.*;

/**
 * @version 2014-12-22
 * @author Patrik Harag
 */
public class EnginePrimitivesServices implements Registerable {
    
    protected final FrameController controller;

    public EnginePrimitivesServices(FrameController controller) {
        this.controller = controller;
    }
    
    // služby
    
    // - processor
    
    public void processorStart() {
        Engine<?, ?> engine = controller.getEngine();
        
        if (engine.isProcessorStopped())
            engine.processorStart();
    }
    
    public void processorStop() {
        Engine<?, ?> engine = controller.getEngine();
        
        if (!engine.isProcessorStopped())
            engine.processorStop();
    }
    
    public void processorSwitch() {
        Engine<?, ?> engine = controller.getEngine();
        
        if (engine.isProcessorStopped())
            engine.processorStart();
        else
            engine.processorStop();
    }
    
    // - renderer
    
    public void rendererStart() {
        Engine<?, ?> engine = controller.getEngine();
        
        if (engine.isRendererStopped())
            engine.rendererStart();
    }
    
    public void rendererStop() {
        Engine<?, ?> engine = controller.getEngine();
        
        if (!engine.isRendererStopped())
            engine.rendererStop();
    }
    
    public void rendererSwitch() {
        Engine<?, ?> engine = controller.getEngine();
        
        if (engine.isRendererStopped())
            engine.rendererStart();
        else
            engine.rendererStop();
    }
    
    // registrační metoda
    
    @Override
    public void register(ServiceManager manager) {
        manager.register(SERVICE_PROCESSOR_START, this::processorStart);
        manager.register(SERVICE_PROCESSOR_STOP, this::processorStop);
        manager.register(SERVICE_PROCESSOR_SWITCH, this::processorSwitch);
        manager.register(SERVICE_RENDERER_START, this::rendererStart);
        manager.register(SERVICE_RENDERER_STOP, this::rendererStop);
        manager.register(SERVICE_RENDERER_SWITCH, this::rendererSwitch);
    }
    
}