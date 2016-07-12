package cz.hartrik.sg2.app;

import cz.hartrik.sg2.engine.Engine;
import cz.hartrik.sg2.engine.ThreadFactoryName;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.application.Platform;

/**
 * Monitoruje změny stavu enginu. <p>
 *
 * Vytvoří vlákno, ve kterém v nekonečné smyčce testuje stav procesoru.
 * Protože procesor může být vypnut mnoha způsoby, toto je jediný způsob.
 *
 * @version 2016-06-26
 * @author Patrik Harag
 */
public class EngineStateMonitor {

    private final ThreadFactory THREAD_FACTORY
            = new ThreadFactoryName("SG2 - engine state monitor");

    private final List<Consumer<ProcessingState>> listeners = new LinkedList<>();
    private final Supplier<Engine<?, ?>> engineSupplier;

    private boolean lastRunning = false;

    public EngineStateMonitor(Supplier<Engine<?, ?>> engineSupplier) {
        this.engineSupplier = engineSupplier;
    }

    public void start() {
        Thread thread = THREAD_FACTORY.newThread(() -> {
            while (true) {
                test();

                try {
                    Thread.sleep(50);

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    private void test() {
        final Engine<?, ?> engine = engineSupplier.get();

        if (engine != null) {
            boolean running = !engine.isProcessorStopped();

            if (lastRunning != running) {
                // změna stavu

                notifyListeners((running)
                        ? ProcessingState.RUNNING
                        : ProcessingState.STOPPED);

                lastRunning = running;
            }
        }
    }

    private void notifyListeners(ProcessingState state) {
        for (Consumer<ProcessingState> consumer : listeners) {
            Platform.runLater(() -> consumer.accept(state));
        }
    }

    public void addOnEngineStateChanged(Consumer<ProcessingState> consumer) {
        listeners.add(consumer);
    }

    public boolean removeOnEngineStateChanged(Consumer<ProcessingState> consumer) {
        return listeners.remove(consumer);
    }

}