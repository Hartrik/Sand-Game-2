package cz.hartrik.sg2.world;

/**
 * Nástroje pro práci s polem elementů rozděleným na chunky.
 *
 * @version 2016-06-21
 * @author Patrik Harag
 * @param <T> typ pole elementů
 */
public abstract class ChunkedAreaTools<T extends ChunkedArea>
        extends ElementAreaTools<T> {

    public ChunkedAreaTools(T area) {
        super(area);
    }

    // resize

    /**
     * Vytvoří nové pole elementů. Zkopíruje původní elementy, případně ořízne
     * z pravé a spodní strany.
     *
     * @param width nová šířka
     * @param height nová výška
     * @param chunkSize velikost chunků
     * @return nově vytvořené pole elementů
     */
    public T resize(int width, int height, int chunkSize) {
        if (width == area.getWidth() && height == area.getHeight()
                && chunkSize == area.getChunkSize()) {

            // beze změny
            return area;
        }

        T newArea = empty(width, height, chunkSize);
        copy(area, newArea);

        return newArea;
    }

    /**
     * Vytvoří nové prázdné pole elementů se stejnými parametry, ale novými
     * rozměry.
     *
     * @param width nová šířka
     * @param height nová výška
     * @param chunkSize velikost chunků
     * @return nové pole elementů
     */
    public abstract T empty(int width, int height, int chunkSize);

    @Override
    public T resize(int width, int height) {
        return super.resize(width, height);
    }

    @Override
    public T empty(int width, int height) {
        return empty(width, height, area.getChunkSize());
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