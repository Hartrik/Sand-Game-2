package cz.hartrik.sg2.brush;

import cz.hartrik.sg2.world.BrushInserter;

/**
 * Rozhraní pro rozlišení štětců, které jsou nanášeny pouze přes jiné elementy.
 * <p>
 *
 * Tento štětec má také zvláštní postavení u třídy {@link BrushInserter}, kde
 * je mu nastaveno {@link BrushInserter#setEraseTemperature(boolean)} na
 * {@code false}, na rozdíl od ostatních štětců.
 *
 * @version 2016-07-02
 * @author Patrik Harag
 */
public interface EffectBrush extends Brush {

}