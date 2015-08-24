
package cz.hartrik.sg2.brush.manage;

import cz.hartrik.common.Checker;
import cz.hartrik.sg2.brush.Brush;

/**
 * Jednoduchý kontejner umožňující nastavit spravovanému štětci další parametry
 * pro potřeby {@link BrushManager}.
 * 
 * @version 2015-01-10
 * @author Patrik Harag
 * @param <T> typ štětce
 */
public class BrushItem<T extends Brush> {
    
    private final T brush;
    private boolean hidden;

    /**
     * Vytvoří novou instanci s <code>hidden = false</code>.
     * 
     * @param brush spravovaný štětec
     */
    public BrushItem(T brush) {
        this(brush, false);
    }

    /**
     * Vytvoří novou instanci.
     * 
     * @param brush spravovaný štětec
     * @param hidden <code>true</code> pro skrytý štětec,
     *               <code>false</code> pro opak
     */
    public BrushItem(T brush, boolean hidden) {
        this.brush = Checker.requireNonNull(brush);
        this.hidden = hidden;
    }

    public T getBrush() {
        return brush;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    
    public boolean isShown()  { return !hidden; }
    public boolean isHidden() { return  hidden; }
    public void hide() { hidden = true; }
    public void show() { hidden = false; }
    
}