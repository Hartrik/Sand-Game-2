
package cz.hartrik.sg2.world;

import java.io.Serializable;

/**
 * Spravuje čtvercovou oblast elementů uvnitř {@link ElementArea}.
 * Jeho hlavním úkolem je sdělovat, zda byl alespoň jeden z elementů uvnitř
 * chunku během cyklu změněn.
 *
 * @version 2017-08-12
 * @author Patrik Harag
 */
public class Chunk implements Serializable {

    private static final long serialVersionUID = 83715083867368_01_004L;

    private final int size;

    private final int x;   // horizontální souřadnice oblasti
    private final int y;   // vertikální souřadnice oblasti

    private final int topLeftX, topLeftY;
    private final int bottomRightX, bottomRightY;

    private volatile boolean changed = true;

    public Chunk(int size, int x, int y) {
        this.size = size;
        this.x = x;
        this.y = y;

        this.topLeftX = x * size;
        this.topLeftY = y * size;
        this.bottomRightX = topLeftX + size - 1;
        this.bottomRightY = topLeftY + size - 1;
    }

    public int getSize() {
        return size;
    }

    public final void change()          { changed = true; }
    public final void change(boolean b) { changed = b; }
    public final boolean isChanged()    { return changed; }

    public final int getChunkX() { return x; }
    public final int getChunkY() { return y; }

    public final int getTopLeftX() { return topLeftX; }
    public final int getTopLeftY() { return topLeftY; }
    public final int getBottomRightX() { return bottomRightX; }
    public final int getBottomRightY() { return bottomRightY; }

    public final boolean contains(int x, int y) {
        return x >= topLeftX && x <= bottomRightX && y >= topLeftY && y <= bottomRightY;
    }

}