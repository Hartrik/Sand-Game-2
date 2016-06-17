
package cz.hartrik.sg2.engine;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.function.BiFunction;
import javafx.scene.image.WritablePixelFormat;

/**
 * Jednoduchý renderer. Po zavolání metody {@link #updateBuffer()} vykreslí
 * do bufferu, obsah plátna. Buffer je možné získat metodou {@link #getBuffer()}.
 * <p>
 * Vykresluje se vždy celé plátno, bez ohledu na aktivitu chunků.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public class JFXRenderer implements Renderer {

    protected final ElementArea area;
    protected final Element[] elements;
    protected final float[] temperature;

    protected final byte[] buffer;
    private final WritablePixelFormat<ByteBuffer> pixelFormat;

    private BiFunction<Element, Float, Color> colorPalette = ColorPalettes.NORMAL;

    @SuppressWarnings("deprecation")
    public JFXRenderer(ElementArea area) {
        this.area = area;
        this.elements = area.getElements();
        this.temperature = area.getTemperature();

        this.buffer = new byte[area.getWidth() * area.getHeight() * 4];
        this.pixelFormat = WritablePixelFormat.getByteBgraPreInstance();

        Arrays.fill(buffer, (byte) (255));
    }

    /**
     * Vykreslí celé plátno do bufferu.
     */
    public synchronized void updateBuffer() {
        for (int i = 0; i < elements.length; i++)
            updateAt(i);
    }

    protected void updateAt(int index) {
        final Color color = getColor(elements[index], temperature[index]);
        index *= 4;

        buffer[index]     = color.getByteBlue();
        buffer[index + 1] = color.getByteGreen();
        buffer[index + 2] = color.getByteRed();
    }

    protected Color getColor(Element element, float temp) {
        return colorPalette.apply(element, temp);
    }

    // settery

    public synchronized void setColorPalette(
            BiFunction<Element, Float, Color> colorPalette) {

        this.colorPalette = colorPalette;
    }

    // gettery

    public final byte[] getBuffer() {
        return buffer;
    }

    public final WritablePixelFormat<ByteBuffer> getPixelFormat() {
        return pixelFormat;
    }

}