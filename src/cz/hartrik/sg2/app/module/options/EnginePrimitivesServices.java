package cz.hartrik.sg2.app.module.options;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.service.Service;
import cz.hartrik.sg2.app.service.ServiceProvider;
import cz.hartrik.sg2.engine.Engine;

/**
 * Poskytuje základní služby pro ovládání enginu.
 *
 * @version 2016-07-09
 * @author Patrik Harag
 */
@ServiceProvider
public class EnginePrimitivesServices {

    public static final String SERVICE_PROCESSOR_STOP = "processor-stop";
    public static final String SERVICE_PROCESSOR_START = "processor-start";
    public static final String SERVICE_PROCESSOR_SWITCH = "processor-switch";
    public static final String SERVICE_RENDERER_STOP = "renderer-stop";
    public static final String SERVICE_RENDERER_START = "renderer-start";
    public static final String SERVICE_RENDERER_SWITCH = "renderer-switch";

    // - processor

    @Service(SERVICE_PROCESSOR_START)
    public void processorStart(Application app) {
        Engine<?, ?> engine = app.getEngine();

        if (engine.isProcessorStopped())
            engine.processorStart();
    }

    @Service(SERVICE_PROCESSOR_STOP)
    public void processorStop(Application app) {
        Engine<?, ?> engine = app.getEngine();

        if (!engine.isProcessorStopped())
            engine.processorStop();
    }

    @Service(SERVICE_PROCESSOR_SWITCH)
    public void processorSwitch(Application app) {
        Engine<?, ?> engine = app.getEngine();

        if (engine.isProcessorStopped())
            engine.processorStart();
        else
            engine.processorStop();
    }

    // - renderer

    @Service(SERVICE_RENDERER_START)
    public void rendererStart(Application app) {
        Engine<?, ?> engine = app.getEngine();

        if (engine.isRendererStopped())
            engine.rendererStart();
    }

    @Service(SERVICE_PROCESSOR_STOP)
    public void rendererStop(Application app) {
        Engine<?, ?> engine = app.getEngine();

        if (!engine.isRendererStopped())
            engine.rendererStop();
    }

    @Service(SERVICE_RENDERER_SWITCH)
    public void rendererSwitch(Application app) {
        Engine<?, ?> engine = app.getEngine();

        if (engine.isRendererStopped())
            engine.rendererStart();
        else
            engine.rendererStop();
    }

}