
package cz.hartrik.sg2.app.extension.canvas;

/**
 * Rozhraní pro kurzor.
 * 
 * @version 2014-04-12
 * @author Patrik Harag
 */
public interface Cursor {

    /**  Nastaví nějakou komponentu, která bude sloužit jako kurzor. */
    void addCursor();
    /** Odstraní kurzor. */
    void removeCursor();
    
    /** Nastaví dodatečné posluchače. */
    void addListeners();
    /** Odebere přidané posluchače. */
    void removeListeners();
    
    /** Posune kurzor na pozici. */
    void onMove(double mX, double mY);
    /** Skryje kurzor - je mimo plátno. */
    void onOver();
    
}