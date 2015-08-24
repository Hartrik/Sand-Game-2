
package cz.hartrik.sg2.world;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Spravuje čtvercovou oblast elementů uvnitř {@link ElementArea}.
 * Jeho hlavním úkolem je sdělovat, zda byl alespoň jeden z elementů uvnitř
 * chunku během cyklu změněn.
 * 
 * @version 2014-04-14
 * @author Patrik Harag
 */
public class Chunk implements Serializable {

    private static final long serialVersionUID = 83715083867368_01_004L;
    
    public static final int CHUNK_SIZE = 100;

    private final int id;  // pořadové číslo oblasti pro rychlé porovnávání
    private final int x;   // horizontální souřadnice oblasti
    private final int y;   // vertikální souřadnice oblasti

    private final ElementArea elementArea;
    
    private final int startX, startY;
    private final int endX, endY;

    private volatile boolean changed  // AtomicBoolean je pomalý, tak snad bude
                             = true;  // toto stačit

    public Chunk(ElementArea elementArea, int x, int y, int id) {
        this.elementArea = elementArea;
        this.x = x;
        this.y = y;
        this.id = id;
        
        this.startX = x * CHUNK_SIZE;
        this.startY = y * CHUNK_SIZE;
        this.endX = startX + CHUNK_SIZE - 1;
        this.endY = startY + CHUNK_SIZE - 1;
    }

    public final void change()          { changed = true; }
    public final void change(boolean b) { changed = b; }
    public final boolean isChanged()    { return changed; }

    public final int getId() { return id; }
    public final int getX()  { return x; }
    public final int getY()  { return y; }

    public final int getBoundMaxX() { return endX; }
    public final int getBoundMinX() { return startX; }
    public final int getBoundMaxY() { return endY; }
    public final int getBoundMinY() { return startY; }
    
    public final boolean contains(int x, int y) {
        return x >= startX && x <= endX && y >= startY && y <= endY;
    }
    
    // iterátory
    
    public void forEach(PointConsumer iterator) {
        for (int nY = startY; nY < endY; nY++)
            for (int nX = startX; nX < endX; nX++)
                iterator.accept(nX, nY);
    }
    
    public void forEach(Consumer<Element> consumer) {
        for (int nY = startY; nY < endY; nY++)
            for (int nX = startX; nX < endX; nX++)
                consumer.accept(elementArea.get(x, y));
    }
    
    // klasické metody objektu
    
    /**
     * Vrátí hash odpovídající {@link #getId() getId()}.
     * 
     * @return hash
     */
    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Porovná objekty na základě {@link #getId() getId()}.
     * 
     * @param object objekt k porovnání
     * @return rovnají se
     */
    @Override
    public boolean equals(Object object) {
        return object != null && getClass() != object.getClass()
                && this.id == ((Chunk) object).id;
    }

}