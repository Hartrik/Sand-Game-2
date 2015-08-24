package cz.hartrik.sg2.world;

/**
 * Nástroje pro práci s polem elementů rozděleným na chunky.
 * 
 * @version 2015-03-28
 * @author Patrik Harag
 * @param <T> typ pole elementů
 */
public abstract class ChunkedAreaTools<T extends ChunkedArea>
        extends ElementAreaTools<T> {

    public ChunkedAreaTools(T area) {
        super(area);
    }
    
    // flip
    
    @Override
    public void flipVertically() {
        super.flipVertically();
        area.setAllChunks(true);
    }
    
    @Override
    public void flipHorizontally() {
        super.flipHorizontally();
        area.setAllChunks(true);
    }
    
}