package cz.hartrik.sg2.engine;

import cz.hartrik.sg2.world.ModularWorld;
import java.util.function.Consumer;

/**
 * Přidává další metody synchronizace, které umožňuje {@link ModularWorld}.
 * 
 * @version 2015-03-07
 * @author Patrik Harag
 */
public class EngineSyncToolsMW extends EngineSyncTools {
    
    protected final ModularWorld modularWorld;

    public EngineSyncToolsMW(Engine<?, ?> engine, ModularWorld modularWorld) {
        super(engine);
        this.modularWorld = modularWorld;
    }
    
    // ---
    
    /**
     * Nastaví runnable ke spuštění na začátku dalšího cyklu.
     * Pokud je procesor zastavený, vykoná metodu run ihned.
     * 
     * @param runnable 
     */
    public void synchronize(Runnable runnable) {
        if (engine.isProcessorStopped())
            runnable.run();
        else
            modularWorld.synchronize(runnable);
    }
    
    /**
     * Nastaví consumer ke spuštění na začátku dalšího cyklu.
     * Pokud je procesor zastavený, vykoná metodu accept ihned.
     * 
     * @param consumer 
     */
    public void synchronize(Consumer<ModularWorld> consumer) {
        if (engine.isProcessorStopped())
            consumer.accept(modularWorld);
        else
            modularWorld.synchronize(consumer);
    }
    
}