package cz.hartrik.sg2.app.module.frame.module;

/**
 * Rozhraní pro třídu poskytující služby.
 * 
 * @version 2014-12-02
 * @author Patrik Harag
 */
public interface Registerable {
    
    /**
     * Zaregistruje jednu nebo více služeb ke správci služeb.
     * 
     * @param manager správce služeb
     */
    public void register(ServiceManager manager);
    
}