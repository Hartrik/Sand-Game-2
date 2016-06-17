package cz.hartrik.sg2.world;

/**
 * Nástroje pro práci se základním polem elementů.
 *
 * @version 2016-06-14
 * @author Patrik Harag
 * @param <T> typ pole elementů
 */
public abstract class ElementAreaTools<T extends ElementArea>
        extends RegionToolsImpl {

    protected final T area;

    public ElementAreaTools(T area) {
        super(area, area);
        this.area = area;
    }

    // rotate, flip

    /**
     * Vrátí nové pole elementů otočené směrem doprava.
     *
     * @return pole elementů
     */
    public T rotateRight() {
        T newArea = empty(area.getHeight(), area.getWidth());
        area.forEachPoint((int x, int y) -> {
            Element element = area.get(x, area.getHeight() - y - 1);
            float temp = area.getTemperature(x, area.getHeight() - y - 1);

            newArea.set(y, x, element);
            newArea.setTemperature(y, x, temp);
        });

        return newArea;
    }

    /**
     * Vrátí nové pole elementů otočené směrem doleva.
     *
     * @return pole elementů
     */
    public T rotateLeft() {
        T newArea = empty(area.getHeight(), area.getWidth());
        area.forEachPoint((int x, int y) -> {
            Element element = area.get(area.getWidth() - x - 1, y);
            float temp = area.getTemperature(area.getWidth() - x - 1, y);

            newArea.set(y, x, element);
            newArea.setTemperature(y, x, temp);
        });

        return newArea;
    }

    /**
     * Vertikálně převrátí pole elementů.
     */
    public void flipVertically() {
        for (int i = 0; i < area.getWidth(); i++)
            for (int j = 0; j < area.getHeight() / 2; j++)
                swap(i, j, i, area.getHeight() - 1 - j);
    }

    /**
     * Horizontálně převrátí pole elementů.
     */
    public void flipHorizontally() {
        for (int i = 0; i < area.getHeight(); i++)
            for (int j = 0; j < area.getWidth() / 2; j++)
                swap(j, i, area.getWidth() - 1 - j, i);
    }

    protected void swap(int x1, int y1, int x2, int y2) {
        Element element1 = area.get(x1, y1);
        Element element2 = area.get(x2, y2);
        area.set(x2, y2, element1);
        area.set(x1, y1, element2);

        float temp1 = area.getTemperature(x1, y1);
        float temp2 = area.getTemperature(x2, y2);
        area.setTemperature(x1, y1, temp2);
        area.setTemperature(x2, y2, temp1);
    }

    /**
     * Vytvoří nové pole elementů. Zkopíruje původní elementy, případně ořízne
     * z pravé a spodní strany.
     *
     * @param width nová šířka
     * @param height nová výška
     * @return nově vytvořené pole elementů
     */
    public T resize(int width, int height) {
        final int oldWidth  = area.getWidth(),
                  oldHeight = area.getHeight();

        if ((width == oldWidth) && (height == oldHeight))
            return area;

        final T newArea = empty(width, height);
        newArea.forEachPoint((int x, int y) -> {
            if (x < oldWidth && y < oldHeight) {
                newArea.set(x, y, area.get(x, y));
                newArea.setTemperature(x, y, area.getTemperature(x, y));
            }
        });

        return newArea;
    }

    /**
     * Vytvoří nové prázdné pole elementů se stejnými parametry, ale novými
     * rozměry.
     *
     * @param width nová šířka
     * @param height nová výška
     * @return nové pole elementů
     */
    public abstract T empty(int width, int height);

}