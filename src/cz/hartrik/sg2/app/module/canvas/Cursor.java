
package cz.hartrik.sg2.app.module.canvas;

/**
 * Rozhraní pro kurzor.
 * 
 * @version 2014-04-12
 * @author Patrik Harag
 */
public interface Cursor {
    
    /**  Nastaví nějakou komponentu, která bude sloužit jako kurzor. */
    public void addCursor();
    /** Odstraní kurzor. */
    public void removeCursor();
    
    /** Nastaví dodatečné posluchače. */
    public void addListeners();
    /** Odebere přidané posluchače. */
    public void removeListeners();
    
    /** Posune kurzor na pozici. */
    public void onMove(double mX, double mY);
    /** Skryje kurzor - je mimo plátno. */
    public void onOver();
    
}