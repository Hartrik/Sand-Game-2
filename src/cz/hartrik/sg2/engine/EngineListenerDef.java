
package cz.hartrik.sg2.engine;

/**
 * Rozhraní pro odchytávání událostí v enginu s <code>default</code> metodami
 * pro snadnější implementaci.
 * 
 * @version 2014-05-24
 * @author Patrik Harag
 */
public interface EngineListenerDef extends EngineListener {
    
    @Override
    public default void processorCycleStart() {};
    
    @Override
    public default void processorCycleEnd() {};
    
    @Override
    public default void rendererCycleStart() {};
    
    @Override
    public default void rendererCycleEnd() {};
    
}