package cz.hartrik.sg2.world;

import cz.hartrik.common.Point;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;

/**
 * Zprostředkuje vkládání elementů do pole elementů.
 * Slouží k tomu, aby mohlo být vkládání elementů řízeno.
 *
 * @version 2016-06-13
 * @author Patrik Harag
 * @param <T> typ pole elementů
 */
public interface Inserter<T extends ElementArea> {

    /**
     * Pokusí se na danou pozici vložit element.
     *
     * @param x horizontální pozice
     * @param y vertikální pozice
     * @param element element, který má být vložen na pozici
     * @return úspěšnost vložení (tj. pokud projde všemi testy)
     */
    public boolean insert(int x, int y, Element element);

    /**
     * Pokusí se na danou pozici vložit element.
     *
     * @param point pozice
     * @param element element, který má být vložen na pozici
     * @return úspěšnost vložení
     */
    public default boolean insert(Point point, Element element) {
        return insert(point.getX(), point.getY(), element);
    }

    /**
     * Pokusí se na danou pozici aplikovat element ze štětce.
     *
     * @param x horizontální pozice
     * @param y vertikální pozice
     * @param brush štětec, který bude použit
     * @return úspěšnost vložení (tj. pokud projde všemi testy)
     */
    public boolean apply(int x, int y, Brush brush);

    /**
     * Pokusí se na danou pozici aplikovat element ze štětce.
     *
     * @param point pozice
     * @param brush štětec, který bude použit
     * @return úspěšnost vložení (tj. pokud projde všemi testy)
     */
    public default boolean apply(Point point, Brush brush) {
        return apply(point.getX(), point.getY(), brush);
    }

    /**
     * Pokusí se na danou pozici vložit element ze štětce.
     *
     * @param x horizontální pozice
     * @param y vertikální pozice
     * @param brush štětec, který bude použit
     * @return úspěšnost vložení (tj. pokud projde všemi testy)
     */
    public boolean insert(int x, int y, Brush brush);

    /**
     * Pokusí se na danou pozici vložit element ze štětce.
     *
     * @param point pozice
     * @param brush štětec, který bude použit
     * @return úspěšnost vložení (tj. pokud projde všemi testy)
     */
    public default boolean insert(Point point, Brush brush) {
        return insert(point.getX(), point.getY(), brush);
    }

    /**
     * Tato metoda se vkládá po dokončení vkládání elementů.
     * Pokud je potřeba na konci provést nějakou operaci...
     */
    public void finalizeInsertion();

    /**
     * Pole elementů, pro které byl tento inserter vytvořen.
     *
     * @return pole elementů
     */
    public T getArea();

    /**
     * Přidá další test. Testuje až elementy, které projdou předchozími testy.
     *
     * @param predicate predikát
     */
    public void appendTest(PointElementPredicate<Element> predicate);

    /**
     * Použije tuto instanci k vytvoření {@link BrushInserter}.
     *
     * @param brush štětec
     * @param controls nastavení
     * @return BrushInserter
     */
    public BrushInserter<T> with(Brush brush, Controls controls);

    /**
     * Použije tuto instanci k vytvoření {@link BrushInserter}.
     *
     * @param brush štětec
     * @return BrushInserter
     */
    public default BrushInserter<T> with(Brush brush) {
        return with(brush, null);
    }

    /**
     * Pokud bude nastaveno na {@code true}, tak inserter před každým vložením
     * elementu nastaví teplotu na daném místě na
     * {@link ElementArea#DEFAULT_TEMPERATURE}. <p>
     *
     * Výchozí hodnota: {@code true}
     *
     * @param eraseTemperature příznak 'vymazání' teploty
     */
    public void setEraseTemperature(boolean eraseTemperature);

}