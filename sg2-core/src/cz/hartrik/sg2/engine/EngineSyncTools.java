
package cz.hartrik.sg2.engine;

import cz.hartrik.sg2.world.ElementArea;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

/**
 * Třída obsahuje nástroje pro synchronizaci s enginem.
 *
 * @version 2016-06-25
 * @author Patrik Harag
 * @param <T>
 */
public class EngineSyncTools<T extends ElementArea> {

    private static final String FORMAT = "SG2 - pause/stop [%03d]";
    private static final ThreadFactory FACTORY = new ThreadFactoryName(FORMAT);

    private final Engine<?, ?> engine;
    private final T elementArea;

    public EngineSyncTools(Engine<?, ?> engine, T elementArea) {
        this.engine = engine;
        this.elementArea = elementArea;
    }

    // --- asynchroní - hlavní vlákno

    /**
     * Předá {@link Runnable} enginu, aby ji bezpečně spustil.
     *
     * @param runnable Runnable
     */
    public void synchronize(Runnable runnable) {
        engine.sync(runnable);
    }

    /**
     * Předá {@link Consumer} enginu, aby ho bezpečně spustil.
     *
     * @param consumer
     */
    public void synchronize(Consumer<T> consumer) {
        engine.sync(() -> consumer.accept(elementArea));
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
    public void stopBothAsync(Runnable runnable) {
        exec(() -> {
            stopBoth();
            runnable.run();
        });
    }

    /**
     * V novém vlákně zastaví procesor a poté zavolá metodu run.
     *
     * @param runnable
     */
    public void stopProcessorAsync(Runnable runnable) {
        exec(() -> {
            stopProcessor();
            runnable.run();
        });
    }

    /**
     * V novém vlákně zastaví renderer a poté zavolá metodu run.
     *
     * @param runnable
     */
    public void stopRendererAsync(Runnable runnable) {
        exec(() -> {
            stopRenderer();
            runnable.run();
        });
    }

    // pause

    /**
     * V novém vlákně volá metodu {@link #pauseBoth(Runnable) pauseBoth}.
     *
     * @param run
     */
    public void pauseBothAsync(Runnable run) {
        exec(() -> pauseBoth(run));
    }

    /**
     * V novém vlákně volá metodu
     * {@link #pauseProcessor(Runnable) pauseProcessor}.
     *
     * @param run
     */
    public void pauseProcessorAsync(Runnable run) {
        exec(() -> pauseProcessor(run));
    }

    /**
     * V novém vlákně volá metodu {@link #pauseRenderer(Runnable) pauseRenderer}.
     *
     * @param run
     */
    public void pauseRendererAsync(Runnable run) {
       exec(() -> pauseRenderer(run));
    }

    private void exec(Runnable runnable) {
        Thread thread = FACTORY.newThread(runnable);
        thread.start();
    }

}