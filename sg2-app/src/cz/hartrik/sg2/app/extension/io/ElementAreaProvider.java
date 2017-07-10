
package cz.hartrik.sg2.app.extension.io;

import cz.hartrik.sg2.world.ElementArea;

/**
 * Rozhraní poskytovatele plátna.
 *
 * @version 2016-06-21
 * @author Patrik Harag
 * @param <T> typ ElementArea
 */
@FunctionalInterface
public interface ElementAreaProvider<T extends ElementArea> {

    /**
     * Vytvoří novou instanci plátna.
     * Parametry nejsou po načtení nijak ošetřeny.
     *
     * @param width šířka plátna
     * @param height výška plátna
     * @param chunkSize velikost chunků
     * @return nové plátno
     */
    T create(int width, int height, int chunkSize);

}