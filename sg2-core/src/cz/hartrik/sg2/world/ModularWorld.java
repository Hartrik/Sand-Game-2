package cz.hartrik.sg2.world;

import cz.hartrik.sg2.world.module.BasicWorldModuleManager;
import cz.hartrik.sg2.world.module.ModularElementArea;
import cz.hartrik.sg2.world.module.SingleAction;
import cz.hartrik.sg2.world.module.SingleActionCon;
import cz.hartrik.sg2.world.module.WorldModuleManager;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * "Svět" podporující moduly.
 *
 * @version 2016-06-21
 * @author Patrik Harag
 */
public class ModularWorld extends World implements ModularElementArea {

    private static final long serialVersionUID = 83715083867368_01_005L;

    private transient WorldModuleManager<ModularWorld> moduleManager;

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {

        in.defaultReadObject();
        moduleManager = new BasicWorldModuleManager<>(this);
    }

    public ModularWorld(int width, int height, int chunkSize, Element background,
            Function<ModularWorld, WorldModuleManager<ModularWorld>> supplier) {

        super(width, height, chunkSize, background);
        moduleManager = supplier.apply(this);
    }

    public ModularWorld(int width, int height, int chunkSize, Element background) {
        super(width, height, chunkSize, background);
        moduleManager = new BasicWorldModuleManager<>(this);
    }

    @Override
    public WorldModuleManager<ModularWorld> getModuleManager() {
        return moduleManager;
    }

    @Override
    public void nextCycle() {
        moduleManager.nextCycle();
    }

    @Override
    public void refresh() {
        moduleManager.refresh();
    }

    /**
     * Na spustí runnable na začátku dalšího cyklu. Není ovšem jisté kdy ani
     * jestli vůbec ke spuštění dojde.
     *
     * @param runnable
     */
    public void synchronize(Runnable runnable) {
        moduleManager.addModule(new SingleAction<>(runnable));
    }

    public void synchronize(Consumer<ModularWorld> consumer) {
        moduleManager.addModule(new SingleActionCon<>(consumer));
    }

    @Override
    public ModularWorldTools<? extends ModularWorld> getTools() {
        return new ModularWorldTools<ModularWorld>(this) {
            @Override
            public ModularWorld empty(int width, int height, int chunkSize) {
                Element bg = area.getBackground();
                ModularWorld world = new ModularWorld(width, height, chunkSize, bg);

                area.getModuleManager().getModules().stream().forEach((module)
                        -> world.getModuleManager().addModule(module));

                return world;
            }
        };
    }

    @Override
    public Inserter<? extends ModularWorld> getInserter() {
        return new ChunkedAreaInserter<>(this);
    }

}