
package cz.hartrik.sg2.engine;

/**
 * Rozhraní pro engine.
 * 
 * @version 2014-12-15
 * @author Patrik Harag
 * @param <P> typ procesoru
 * @param <R> typ rendereru
 */
public interface Engine<P extends Processor, R extends Renderer> {
    
    public P getProcessor();
    public R getRenderer();
    
    public void rendererStart();
    public void rendererStop();
    public boolean isRendererStopped();
    
    public void processorStart();
    public void processorStop();
    public boolean isProcessorStopped();
    
    public int getCurrentCycles();
    public int getCurrentFPS();
    public void setMaxCycles(int cycles);
    public void setMaxFPS(int fps);
    public int getUpdatedChunksCount();
    
    public void setListener(EngineListener listener);
    
}