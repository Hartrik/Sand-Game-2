
package cz.hartrik.sg2.world.element.fluid;

import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.special.DesiccatingPowder;
import cz.hartrik.sg2.world.element.special.Sponge;

/**
 * Rozhraní pro elementy, které mohou být vysušeny. Rozhraní je spojené např. s
 * chováním elementů {@link DesiccatingPowder} a {@link Sponge}.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public interface Dryable extends Element {

    /**
     * Vrátí "vysušený" element. <p>
     * Návratová hodnota může být i {@code null}, to potom znamená, že byl
     * element při vysoušení zcela zničen.
     *
     * @return element
     */
    public Element dry();

    /**
     * Vysuší element na daném místě.
     *
     * @param x horizontální pozice
     * @param y vertikální pozice
     * @param dryable element k vysušení
     * @param world plátno
     */
    public static void dryElementAt(int x, int y, Dryable dryable, World world) {
        Element dried = dryable.dry();
        if (dried == null)
            dried = world.getBackground();

        world.setAndChange(x, y, dried);
        if (!dried.hasTemperature())
            world.setTemperature(x, y, World.DEFAULT_TEMPERATURE);
    }

}