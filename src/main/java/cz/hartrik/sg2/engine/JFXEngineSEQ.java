package cz.hartrik.sg2.engine;

import cz.hartrik.sg2.engine.render.JFXRenderer;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

/**
 *
 *
 * @version 2016-06-26
 * @author Patrik Harag
 * @param <P>
 */
@SuppressWarnings("deprecation")
public class JFXEngineSEQ <P extends Processor> implements JFXEngine<P> {

    private static final ThreadFactory mainThreadFactory
            = new ThreadFactoryName("SG2 - engine/main [%03d]");

    private static final ThreadFactory rendererThreadFactory
            = new ThreadFactoryName("SG2 - engine/renderer [%03d]");

    private final ExecutorService rendererExec
            = Executors.newSingleThreadExecutor(rendererThreadFactory);
    private Future<?> renderFuture;

    private final ExecutorService mainExec
            = Executors.newSingleThreadExecutor(mainThreadFactory);
    private Future<?> mainFuture;

    // ----

    private final P processor;
    private final JFXRenderer renderer;

    private final World world;
    private EngineListener listener;
    private final Queue<Runnable> queue = new ConcurrentLinkedQueue<>();
    private final FPSCounter rCounter, pCounter;

    private final Canvas canvas;

    final Element[] eBuffer;
    final float[] tBuffer;

    public JFXEngineSEQ(World world, P processor, JFXRenderer renderer,
            Canvas canvas) {

        this.world = world;
        this.processor = processor;
        this.renderer = renderer;
        this.canvas = canvas;

        this.rCounter = new FPSCounter();
        this.pCounter = new FPSCounter();

        this.listener = new EngineListenerDef() {};
        this.rendererAnimationTime = new RendererAnimation();

        this.eBuffer = new Element[world.getElements().length];
        this.tBuffer = new float[world.getTemperature().length];

        canvas.setWidth(world.getWidth());
        canvas.setHeight(world.getHeight());
    }

    // --- PROCESSOR ---

    private volatile boolean pPause = true;
    private volatile boolean pStopped = true;

    private final Object pMaxSleepLock = new Object();
    private int pMaxSleep = 1000 / 20;

    @Override
    public synchronized void processorStart() {
        pPause = false;
        pStopped = false;

        if (mainFuture == null || mainFuture.isDone())
            setUpMainThread();
    }

    @Override
    public synchronized void processorStop() {
        pPause = true;
    }

    @Override
    public synchronized boolean isProcessorStopped() {
        return pStopped;
    }

    // --- RENDERER ---

    private volatile boolean rPause = true;
    private volatile boolean rStopped = true;

    @Override
    public synchronized void rendererStart() {
        rPause = false;
        rStopped = false;

        if (mainFuture == null || mainFuture.isDone())
            setUpMainThread();
    }

    @Override
    public synchronized void rendererStop() {
        rPause = true;
    }

    @Override
    public synchronized boolean isRendererStopped() {
        return rStopped;
    }

    // hlavní vlákno

    private void setUpMainThread() {
        Runnable mainLoop = () -> {
            try {
                while ((pStopped && rStopped) == false) {
                    final long startTime = System.currentTimeMillis();

                    if (!pStopped) {
                        processorCycle();
                    }

                    if (renderFuture == null || renderFuture.isDone()) {
                        // tady jsou vlákna synchronizovaná
                        processQueue();

                        if (!rStopped) {
                            copyArrays();

                            Runnable r = this::rendererCycle;
                            renderFuture = rendererExec.submit(r);
                        }
                    }

                    sleep(mainLoopSleepTime(startTime, System.currentTimeMillis()));
                }

            } finally {
                // po opravení chyby se může opět spustit
                rPause = true;
                pPause = true;
                rStopped = true;
                pStopped = true;
            }
        };

        mainFuture = mainExec.submit(mainLoop);

        rendererAnimationTime.start();
    }

    /**
     * Zkopíruje pole elementů a pole s jejich teplotou do renrereru.
     * Tato pole tak budou při vykreslování statická a nebude docházet k
     * chybám při synchronizaci atd, a tedy ani k blikání.
     */
    private void copyArrays() {
        System.arraycopy(world.getElements(), 0, eBuffer, 0, eBuffer.length);
        renderer.setElements(eBuffer);

        System.arraycopy(world.getTemperature(), 0, tBuffer, 0, tBuffer.length);
        renderer.setTemperature(tBuffer);
    }

    private void processorCycle() {
        listener.processorCycleStart();
        processor.nextCycle();

        final long currentTimeMillis = System.currentTimeMillis();

        pCounter.tick(currentTimeMillis);
        listener.processorCycleEnd();

        if (pPause) pStopped = true;
    }

    private void rendererCycle() {
        listener.rendererCycleStart();

        renderer.updateBuffer();
        // vykreslení plátno do bufferu

        synchronized (rendererAnimationTime) {
            updated = true;

            while (updated) {
                try {
                    rendererAnimationTime.wait();
                    // čakáme až JavaFX prostřednictvím naší "animace" vykreslí
                    // plátno (to se děje obvykle 60× za sekundu)

                } catch (InterruptedException ignored) {
                    // teď to není možné přerušit
                }
            }
        }

        final long currentTimeMillis = System.currentTimeMillis();
        rCounter.tick(currentTimeMillis);

        listener.rendererCycleEnd();

        if (rPause) rStopped = true;
    }

    private final AnimationTimer rendererAnimationTime;

    // sync: rendererAnimationTime
    private boolean updated;

    protected class RendererAnimation extends AnimationTimer {
        @Override
        public void handle(long now) {
            synchronized (rendererAnimationTime)  {
                if (updated) {
                    canvas.getGraphicsContext2D().getPixelWriter().setPixels(
                            0, 0, world.getWidth(), world.getHeight(),
                            renderer.getPixelFormat(), renderer.getBuffer(), 0,
                            (world.getWidth() * 4));

                    updated = false;
                    rendererAnimationTime.notifyAll();
                }

                if (rStopped) {
                    rendererAnimationTime.stop();
                }
            }
        }
    }

    private void sleep(long millis) {
        if (millis == 0) return;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException interrupted) {
            throw new RuntimeException(interrupted);
        }
    }

    private long mainLoopSleepTime(
            final long cycleStartTime, final long cycleEndTime) {

        final long diff = cycleEndTime - cycleStartTime;
        synchronized (pMaxSleepLock) {
            return diff < pMaxSleep ? pMaxSleep - diff : pMaxSleep;
        }
    }

    @Override
    public void shutdown() {
        processorStop();
        rendererStop();

        mainExec.shutdown();
        rendererExec.shutdown();
        processor.shutdown();
    }

    // synchronizované akce

    private void processQueue() {
        Runnable runnable;
        while ((runnable = queue.poll()) != null) {
            runnable.run();
        }
    }

    @Override
    public void sync(Runnable runnable) {
        queue.add(runnable);
    }

    // gettery, settery

    @Override
    public P getProcessor() {
        return processor;
    }

    @Override
    public JFXRenderer getRenderer() {
        return renderer;
    }

    @Override
    public int getCurrentFPS() {
        return rCounter.getFPS();
    }

    @Override
    public int getCurrentCycles() {
        return pCounter.getFPS();
    }

    @Override
    public int getUpdatedChunksCount() {
        return processor.getLastUpdatedChunks();
    }

    @Override
    public void setListener(EngineListener listener) {
        this.listener = listener;
    }

    @Override
    public void setMaxCycles(int cycles) {
        synchronized (pMaxSleepLock) {
            pMaxSleep = 1000/cycles;
        }
    }

    @Override
    public void setMaxFPS(int fps) {
        // není možné nastavit
    }

}