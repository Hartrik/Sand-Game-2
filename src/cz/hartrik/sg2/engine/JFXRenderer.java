
package cz.hartrik.sg2.engine;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Chunk;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.function.Function;
import javafx.scene.image.WritablePixelFormat;

import static cz.hartrik.sg2.world.Chunk.CHUNK_SIZE;

/**
 * Jednoduchý renderer, který se stará o aktualizaci bufferu.
 *
 * @version 2016-05-23
 * @author Patrik Harag
 */
public class JFXRenderer implements Renderer {

    protected final ElementArea area;
    private final Element[] array;
    private final int width;
    private final int height;

    protected final byte[] data;
    private int lastUpdatedChunks;
    private final WritablePixelFormat<ByteBuffer> pixelFormat;

    private Function<Element, Color> colorPalette = ColorPalettes.NORMAL;

    public JFXRenderer(ElementArea area) {
        this.area = area;
        this.array = area.getArray();
        this.width = area.getWidth();
        this.height = area.getHeight();

        this.data = new byte[width * height * 4];
        this.pixelFormat = WritablePixelFormat.getByteBgraPreInstance();

        Arrays.fill(data, (byte) (255));
    }

    // vykreslování po chuncích

    @Deprecated
    public synchronized void updateBuffer(Chunk[] chunks) {
        for (Chunk chunk : chunks)
            updateChunk(chunk.getX(), chunk.getY());
        lastUpdatedChunks = chunks.length;
    }

    @Deprecated
    protected final void updateChunk(final int cx, final int cy) {
        final int maxY = cy * CHUNK_SIZE + CHUNK_SIZE;
        for (int y = cy * CHUNK_SIZE; y < maxY; y++) {
            final int maxX = cx * CHUNK_SIZE + CHUNK_SIZE;
            for (int x = cx * CHUNK_SIZE; x < maxX; x++) {
                updateColor(x, y);
            }
        }
    }

    // vykreslování celého plátna najednou

    public synchronized void updateBufferAll() {
        for (int i = 0; i < array.length; i++) updateColor(i);

        // pro velká plátna raději paralelně:
        //
        // IntStream.range(0, array.length)
        //          .parallel()
        //          .forEach(this::updateColor);

        lastUpdatedChunks = width / Chunk.CHUNK_SIZE * height / Chunk.CHUNK_SIZE;
    }

    // pomocné metody

    protected final void updateColor(final int x, final int y) {
        updateColor(y * width + x);
    }

    protected void updateColor(int index) {
        final Color color = getColor(array[index]);
        index *= 4;

        data[index]     = color.getByteBlue();
        data[index + 1] = color.getByteGreen();
        data[index + 2] = color.getByteRed();
    }

    protected Color getColor(Element element) {
        return colorPalette.apply(element);
    }

    // settery

    public void setColorPalette(Function<Element, Color> colorPalette) {
        this.colorPalette = colorPalette;
    }

    // gettery

    public final byte[] getData() {
        return data;
    }

    public final WritablePixelFormat<ByteBuffer> getPixelFormat() {
        return pixelFormat;
    }

    public final int getLastUpdatedChunks() {
        return lastUpdatedChunks;
    }

}