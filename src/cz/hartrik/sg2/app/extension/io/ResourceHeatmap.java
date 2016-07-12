package cz.hartrik.sg2.app.extension.io;

import cz.hartrik.common.Point;
import cz.hartrik.sg2.world.ElementArea;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import org.w3c.dom.Node;

/**
 * Teplotní mapa uložená v obrázku.
 *
 * @version 2016-06-19
 * @author Patrik Harag
 */
public class ResourceHeatmap implements ResourceType {

    public static final String IDENTIFIER = "heatmap template";

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public void writeXML(SimpleDOM dom) {
        // není potřeba
    }

    @Override
    public void writeData(OutputStream out, ElementArea area) throws IOException {
            ImageIO.write(createImage(area), "png", out);
    }

    protected BufferedImage createImage(ElementArea area) {
        BufferedImage bufferedImage = new BufferedImage(
                area.getWidth(), area.getHeight(), BufferedImage.TYPE_INT_RGB);


        area.forEachPoint((x, y) -> {
            float temperature = area.getTemperature(x, y);
            bufferedImage.setRGB(x, y, toARGB(temperature));
        });

        return bufferedImage;
    }

    private static int toARGB(float temperature) {
        int t = (int) temperature;
        return ~t;
    }

    @Override
    public void loadData(InputStream in, Node node, ElementArea area)
            throws IOException {

        BufferedImage image = ImageIO.read(in);
        Point position = ParseUtils.parsePosition(node);

        apply(area, image, position.getX(), position.getY());
    }

    protected void apply(ElementArea area, BufferedImage image, int x, int y) {
        final int imgWidth = image.getWidth();
        final int imgHeight = image.getHeight();

        final int areaWidth = area.getWidth();
        final int areaHeight = area.getHeight();

        for (int cX = x; cX < (x + imgWidth) && cX < areaWidth; cX++) {
            for (int cY = y; cY < (y + imgHeight) && cY < areaHeight; cY++) {

                if (cX < 0 || cY < 0) continue;

                float temperature = fromARGB(image.getRGB(cX - x, cY - y));
                area.setTemperature(cX, cY, temperature);
            }
        }
    }

    private static float fromARGB(int argb) {
        return ~argb;
    }

}