package cz.hartrik.sg2.engine;

import cz.hartrik.sg2.world.World;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

/**
 * Typ enginu, který k vykreslování využívá vlákno, které sám spravuje.
 * Na rozdíl od {@link JFXEngineAT} umožňuje lépe pracovat se snímkovou
 * frekvencí.
 * 
 * @version 2014-12-22
 * @author Patrik Harag
 * @param <P> typ procesoru
 */
public class JFXEngineT<P extends Processor> extends JFXEngine<P> {

    private boolean rPause = true;
    private boolean rStopped = true;
    private Thread rThread;
    private static int rThreadId = 0;
    
    protected int rMaxSleep = 1000/55;
    
    public JFXEngineT(World world, P processor, JFXRenderer renderer, ImageView view) {
        super(world, processor, renderer, view);
    }
    
    @Override
    public synchronized void rendererStart() {
        if (rPause)
            rPause = false;
        else
            return;

        rStopped = false;
        rThread = new Thread(() -> {
            try { // problém se může odstranit a renderer se může opět spustit
                long startTime;
            
                while (!rPause) {
                    startTime = System.currentTimeMillis();
                    listener.rendererCycleStart();
                    renderer.updateBufferAll();

                    if (renderer.getLastUpdatedChunks() != 0) {
                        FutureTask<Void> task = setPixels();
                        try {
                            task.get(); // blokuje
                        } catch (InterruptedException | ExecutionException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    rCounter.tick();
                    listener.rendererCycleEnd();

                    if (rPause) break;
                    else {
                        long diff = System.currentTimeMillis() - startTime;
                        sleep(diff < rMaxSleep ? rMaxSleep - diff : rMaxSleep);
                    }
                }
            } finally {
                rStopped = true;
                rPause = true;
            }
        }, "SG2 Engine - renderer thread - " + (++rThreadId));
        rThread.start();
    }
    
    @Override
    public synchronized void rendererStop() {
        if (!rPause) rPause = true;
    }
    
    @Override
    public synchronized boolean isRendererStopped() {
        return rStopped;
    }
    
    private FutureTask<Void> setPixels() {
        final FutureTask<Void> task = new FutureTask<>(() -> {
            mainWriter.setPixels(
                    0, 0, world.getWidth(), world.getHeight(),
                    renderer.getPixelFormat(), renderer.getData(), 0,
                    (world.getWidth() * 4));
            return null;
        });
        Platform.runLater(task);
        return task;
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
    public void setMaxFPS(int fps) {
        rMaxSleep = 1000/fps;
    }
    
}