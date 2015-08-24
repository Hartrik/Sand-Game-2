
package cz.hartrik.sg2.engine;

/**
 * Třída obsahuje nástroje pro synchronizaci s enginem.
 * 
 * @version 2015-04-07
 * @author Patrik Harag
 */
public class EngineSyncTools {
    
    protected static volatile int tCount = 0;
    protected static final String PREFIX_PAUSE = "SG2 Engine - pause/stop - ";
    
    protected final Engine<?, ?> engine;

    public EngineSyncTools(Engine<?, ?> engine) {
        this.engine = engine;
    }
    
    // --- základní - neblokující
    
    /**
     * Dá pokyn k zastavení procesoru i rendereru.
     */
    public void stopBothLazy() {
        engine.processorStop();
        engine.rendererStop();
    }
    
    /**
     * Dá pokyn k zastavení procesoru.
     */
    public void stopProcessorLazy() {
        engine.processorStop();
    }
    
    /**
     * Dá pokyn k zastavení rendereru.
     */
    public void stopRendererLazy() {
        engine.rendererStop();
    }
    
    // --- blokující
    
    // stop
    
    /**
     * Zastaví procesor i renderer, <b>blokuje</b>.
     */
    public void stopBoth() {
        engine.processorStop();
        engine.rendererStop();
        while (!engine.isProcessorStopped() || !engine.isRendererStopped()) {}
    }
    
    /**
     * Zastaví procesor, <b>blokuje</b>.
     */
    public void stopProcessor() {
        engine.processorStop();
        while (!engine.isProcessorStopped()) {}
    }
    
    /**
     * Zastaví renderer, <b>blokuje</b>.
     */
    public void stopRenderer() {
        engine.rendererStop();
        while (!engine.isRendererStopped()) {}
    }
    
    // pause
    
    /**
     * Zastaví procesor a renderer, zavolá metodu run a opět spustí
     * procesor a renderer, <b>blokuje</b>.
     * 
     * @param runnable
     */
    public void pauseBoth(Runnable runnable) {
        boolean rendererRunning = !engine.isRendererStopped();
        boolean processorRunning = !engine.isProcessorStopped();
        
        stopBoth();  // blokuje
        runnable.run();
        
        if (rendererRunning) engine.rendererStart();
        if (processorRunning) engine.processorStart();
    }
    
    /**
     * Zastaví procesor, zavolá metodu run a opět spustí procesor,
     * <b>blokuje</b>.
     * 
     * @param runnable 
     */
    public void pauseProcessor(Runnable runnable) {
        boolean processorRunning = !engine.isProcessorStopped();
        
        stopProcessor();  // blokuje
        runnable.run();
        
        if (processorRunning) engine.processorStart();
    }
    
    /**
     * Zastaví renderer, zavolá metodu run a opět spustí renderer,
     * <b>blokuje</b>.
     * 
     * @param runnable 
     */
    public void pauseRenderer(Runnable runnable) {
        boolean rendererRunning = !engine.isRendererStopped();
        
        stopRenderer();  // blokuje
        runnable.run();
        
        if (rendererRunning) engine.rendererStart();
    }
    
    // --- blokující / lazy
    
    /**
     * Metoda dá pokyn k zastavení procesoru a rendereru, zavolá metodu run a
     * opět spustí procesor a renderer. Během vykonávání metody run mohou být
     * vlákna procesoru a rendereru ještě spuštěná.
     * 
     * @param runnable 
     */
    public void pauseBothLazy(Runnable runnable) {
        boolean rendererRunning = !engine.isRendererStopped();
        boolean processorRunning = !engine.isProcessorStopped();
        
        engine.processorStop();
        engine.rendererStop();
        
        runnable.run();
        
        if (rendererRunning) engine.rendererStart();
        if (processorRunning) engine.processorStart();
    }
    
    /**
     * Metoda dá pokyn k zastavení procesoru, zavolá metodu run a opět spustí
     * procesor. Během vykonávání metody run mohou být vlákna procesoru
     * ještě spuštěná.
     * 
     * @param runnable 
     */
    public void pauseProcessorLazy(Runnable runnable) {
        boolean processorRunning = !engine.isProcessorStopped();
        
        engine.processorStop();
        runnable.run();
        
        if (processorRunning) engine.processorStart();
    }
    
    /**
     * Metoda dá pokyn k zastavení rendereru, zavolá metodu run a opět spustí
     * renderer. Během vykonávání metody run mohou být vlákna rendereru
     * ještě spuštěná.
     * 
     * @param runnable 
     */
    public void pauseRendererLazy(Runnable runnable) {
        boolean rendererRunning = !engine.isRendererStopped();
        
        engine.rendererStop();
        runnable.run();
        
        if (rendererRunning) engine.rendererStart();
    }
    
    // --- neblokující
    
    // stop
    
    /**
     * V novém vlákně zastaví procesor i renderer a zavolá metodu run.
     * 
     * @param runnable 
     */
    public void stopBothThread(Runnable runnable) {
        new Thread(() -> { stopBoth(); runnable.run(); },
                PREFIX_PAUSE + tCount++).start();
    }
    
    /**
     * V novém vlákně zastaví procesor a zavolá metodu run.
     * 
     * @param runnable 
     */
    public void stopProcessorThread(Runnable runnable) {
        new Thread(() -> { stopProcessor(); runnable.run(); },
                PREFIX_PAUSE + tCount++).start();
    }
    
    /**
     * V novém vlákně zastaví renderer a zavolá metodu run.
     * 
     * @param runnable 
     */
    public void stopRendererThread(Runnable runnable) {
        new Thread(() -> { stopRenderer(); runnable.run(); },
                PREFIX_PAUSE + tCount++).start();
    }
    
    // pause
    
    /**
     * V novém vlákně volá metodu {@link #pauseBoth(Runnable) pauseBoth}.
     * 
     * @param run 
     */
    public void pauseBothThread(Runnable run) {
        new Thread(() -> pauseBoth(run), PREFIX_PAUSE + tCount++).start();
    }
    
    /**
     * V novém vlákně volá metodu
     * {@link #pauseProcessor(Runnable) pauseProcessor}.
     * 
     * @param run 
     */
    public void pauseProcessorThread(Runnable run) {
        new Thread(() -> pauseProcessor(run), PREFIX_PAUSE + tCount++).start();
    }
    
    /**
     * V novém vlákně volá metodu {@link #pauseRenderer(Runnable) pauseRenderer}.
     * 
     * @param run 
     */
    public void pauseRendererThread(Runnable run) {
        new Thread(() -> pauseRenderer(run), PREFIX_PAUSE + tCount++).start();
    }
    
}