package cz.hartrik.sg2.world;

/**
 * Nástroje pro práci s {@link ModularWorld}.
 * 
 * @version 2014-12-20
 * @author Patrik Harag
 * @param <T> typ pole elementů
 */
public abstract class ModularWorldTools <T extends ModularWorld>
        extends WorldTools<T> {

    public ModularWorldTools(T world) {
        super(world);
    }
    
}