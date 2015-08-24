package cz.hartrik.sg2.brush.manage;

import cz.hartrik.sg2.brush.Brush;
import java.util.LinkedList;
import java.util.List;

/**
 * @version 2015-01-10
 * @author Patrik Harag
 * @param <T>
 */
public class ObservedBrushManager<T extends Brush> extends BrushManager<T> {

    protected final List<Runnable> listeners = new LinkedList<>();
    
    @Override
    public void addBrushItem(BrushItem<T> brushItem) {
        super.addBrushItem(brushItem);
        notifyListeners();
    }

    @Override
    public boolean setHidden(int id, boolean hidden) {
        if (super.setHidden(id, hidden)) {
            notifyListeners();
            return true;
        }
        return false;
    }
    
    private void notifyListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }
    
    public void addListener(Runnable runnable) {
        listeners.add(runnable);
    }
    
    public void removeListener(Runnable runnable) {
        listeners.remove(runnable);
    }
    
}