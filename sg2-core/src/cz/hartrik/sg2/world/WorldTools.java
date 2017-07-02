
package cz.hartrik.sg2.world;

/**
 * Různé nástroje pro manipulaci s instancí třídy {@link World}.
 * 
 * @version 2014-12-31
 * @author Patrik Harag
 * @param <T> typ pole elementů
 */
public abstract class WorldTools<T extends World> extends ChunkedAreaTools<T> {
    
    public WorldTools(T world) {
        super(world);
    }

    /**
     * Smaže obsah plátna.
     * Závislé na {@link World#getBackground()}.
     */
    public void clear() {
        fill(area.getBackground());
    }
    
}