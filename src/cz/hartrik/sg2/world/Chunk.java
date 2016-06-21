
package cz.hartrik.sg2.world;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Spravuje čtvercovou oblast elementů uvnitř {@link ElementArea}.
 * Jeho hlavním úkolem je sdělovat, zda byl alespoň jeden z elementů uvnitř
 * chunku během cyklu změněn.
 *
 * @version 2016-06-21
 * @author Patrik Harag
 */
public class Chunk implements Serializable {

    private static final long serialVersionUID = 83715083867368_01_004L;

    private final int size;

    private final int x;   // horizontální souřadnice oblasti
    private final int y;   // vertikální souřadnice oblasti

    private final ElementArea elementArea;

    private final int startX, startY;
    private final int endX, endY;

    private volatile boolean changed = true;

    public Chunk(ElementArea elementArea, int size, int x, int y) {
        this.elementArea = elementArea;
        this.size = size;
        this.x = x;
        this.y = y;

        this.startX = x * size;
        this.startY = y * size;
        this.endX = startX + size - 1;
        this.endY = startY + size - 1;
    }

    public int getSize() {
        return size;
    }

    public final void change()          { changed = true; }
    public final void change(boolean b) { changed = b; }
    public final boolean isChanged()    { return changed; }

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

}