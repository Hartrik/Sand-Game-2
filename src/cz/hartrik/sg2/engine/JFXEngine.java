
package cz.hartrik.sg2.engine;

import cz.hartrik.sg2.world.World;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Engine vykreslující do obrázku frameworku JavaFX.
 * 
 * @version 2014-12-22
 * @author Patrik Harag
 * @param <P>
 */
public abstract class JFXEngine<P extends Processor>
        implements Engine<P, JFXRenderer> {
    
    protected final P processor;
    protected final JFXRenderer renderer;
    
    protected final World world;
    protected EngineListener listener;
    protected final FPSCounter rCounter, pCounter;
    
    protected final ImageView imageView;
    protected final WritableImage mainImage;
    protected final PixelWriter mainWriter;
    
    public JFXEngine(World world, P processor, JFXRenderer renderer,
            ImageView view) {
        
        this.world = world;
        this.processor = processor;
        this.renderer = renderer;
        this.imageView = view;
        
        this.rCounter = new FPSCounter();
        this.pCounter = new FPSCounter();
        
        this.listener = new EngineListenerDef() {};
        this.mainImage = new WritableImage(world.getWidth(), world.getHeight());
        this.mainWriter = mainImage.getPixelWriter();

        imageView.setImage(mainImage);
        imageView.setFitWidth(world.getWidth());
        imageView.setFitHeight(world.getHeight());
    }

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
    
    // procesor
    
    private boolean pPause = true;
    private boolean pStopped = true;
    private Thread pThread;
    private static int pThreadId = 0;
    protected int pMaxSleep = 1000 / 20;
    
    @Override
    public synchronized void processorStart() {
        if (pPause)
            pPause = false;
        else
            return;

        pStopped = false;
        pThread = new Thread(() -> {
            try { // po opravení chyby se může opět spustit
                long startTime;
                while (!pPause) {
                    startTime = System.currentTimeMillis();

                    pCounter.tick();
                    listener.processorCycleStart();
                    processor.nextCycle();
                    listener.processorCycleEnd();

                    if (pPause) break;
                    else {
                        long diff = System.currentTimeMillis() - startTime;
                        sleep(diff < pMaxSleep ? pMaxSleep - diff : pMaxSleep);
                    }
                }
            } finally {
                pStopped = true;
                pPause = true;
            }
        }, "SG2 Engine - processor thread - " + (++pThreadId));
        pThread.start();
    }

    private void sleep(long millis) {
        if (millis == 0) return;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException interrupted) {
            throw new RuntimeException(interrupted);
        }
    }
    
    @Override
    public synchronized void processorStop() {
        if (!pPause) pPause = true;
    }

    @Override
    public synchronized boolean isProcessorStopped() {
        return pStopped;
    }

    @Override
    public void setMaxCycles(int cycles) {
        pMaxSleep = 1000/cycles;
    }
    
}