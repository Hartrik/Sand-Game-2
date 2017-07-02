
package cz.hartrik.sg2.engine;

/**
 * Rozhraní pro listener odchytávající události v enginu.
 * 
 * @version 2014-03-23
 * @author Patrik Harag
 */
public interface EngineListener {
    
    public void processorCycleStart();
    
    public void processorCycleEnd();
    
    public void rendererCycleStart();
    
    public void rendererCycleEnd();
    
}