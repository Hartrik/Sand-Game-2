package cz.hartrik.sg2.brush.manage;

import java.util.LinkedList;
import java.util.List;

/**
 * @version 2015-01-10
 * @author Patrik Harag
 */
public class ObservedBrushManager extends BrushManager {

    protected final List<Runnable> listeners = new LinkedList<>();

    @Override
    public void addBrushItem(BrushItem brushItem) {
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