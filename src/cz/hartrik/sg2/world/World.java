
package cz.hartrik.sg2.world;

/**
 * "Svět" z elementů.
 * 
 * @version 2015-01-12
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
     * @param background pozadí - element, kterým bude naplněn
     */
    public World(int width, int height, Element background) {
        super(width, height);
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
            public World empty(int width, int height) {
                return new World(width, height, area.getBackground());
            }
        };
    }

    @Override
    public Inserter<? extends World> getInserter() {
        return new ChunkedAreaInserter<>(this);
    }
    
}