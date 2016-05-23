package cz.hartrik.sg2.engine;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.temperature.Thermal;
import java.util.stream.IntStream;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

/**
 * Přiděluje elementům barvu podle teploty.
 *
 * @version 2016-05-23
 * @author Patrik Harag
 */
public class HeatmapColorPalette {

    private final int size;
    private final Color[] lookupTable;
    private final int minTemp;
    private final int maxTemp;

    private Color airColor = Color.WHITE;
    private Color defColor = Color.BLACK;

    /**
     * Vytvoří novou instanci.
     *
     * @param gradient obrázek s gradientem (použije se první řádka)
     * @param minTemp minimální teplota
     * @param maxTemp maximální teplota
     */
    public HeatmapColorPalette(Image gradient, int minTemp, int maxTemp) {
        this.size = (int) gradient.getWidth() - 1;
        this.lookupTable = createLookupTable(gradient);

        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    private Color[] createLookupTable(Image gradient) {
        final PixelReader pixelReader = gradient.getPixelReader();

        return IntStream.range(0, (int) gradient.getWidth())
                .map(i -> pixelReader.getArgb(i, 0))
                .mapToObj(Color::createARGB)
                .toArray(Color[]::new);
    }

    /**
     * Vrátí barvu elementu podle jeho teploty.
     *
     * @param element element
     * @return barva
     */
    public Color color(Element element) {
        if (element instanceof Thermal) {
            double relTemp = relativize(((Thermal) element).getTemperature());
            return lookupTable[(int) (relTemp * size)];
        }

        return (element instanceof Air) ? airColor : defColor;
    }

    private double relativize(int temperature) {
        double r = ((double) temperature - minTemp) / (maxTemp - minTemp);

        if (r < 0)
            return 0;
        else if (r > 1)
            return 1;
        else
            return r;
    }

    /**
     * Nastaví barvu pro vzduch, výchozí barva je {@link Color#White}.
     *
     * @param airColor barva
     */
    public void setAirColor(Color airColor) {
        this.airColor = airColor;
    }

    /**
     * Nastaví barva pro elementy, které nemají žádnou teplotu,
     * výchozí barva je {@link Color#White}.
     *
     * @param defColor barva
     */
    public void setDefaultColor(Color defColor) {
        this.defColor = defColor;
    }

}