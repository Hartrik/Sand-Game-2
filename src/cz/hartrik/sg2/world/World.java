
package cz.hartrik.sg2.world;

/**
 * "Svět" z elementů.
 *
 * @version 2016-06-21
 * @author Patrik Harag
 */
public class World extends ChunkedArea {

    private static final long serialVersionUID = 83715083867368_01_003L;

    private final Element background;

    /**
     * Vytvoří nový svět složený z elementů.
     *
     * @param width šířka
     * @param height výška
     * @param chunkSize velikost chunků
     * @param background pozadí - element, kterým bude naplněn
     */
    public World(int width, int height, int chunkSize, Element background) {
        super(width, height, chunkSize);
        this.background = background;

        forEachPoint((x, y) -> set(x, y, background));
    }

    public final Element getBackground() {
        return background;
    }

    @Override
    public WorldTools<? extends World> getTools() {
        return new WorldTools<World>(this) {
            @Override
            public World empty(int width, int height, int chunkSize) {
                Element background = area.getBackground();
                return new World(width, height, chunkSize, background);
            }
        };
    }

    @Override
    public Inserter<? extends World> getInserter() {
        return new ChunkedAreaInserter<>(this);
    }

}