package cz.hartrik.sg2.app.module.io;

import cz.hartrik.common.Checker;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import javax.imageio.ImageIO;

/**
 * @version 2015-03-13
 * @author Patrik Harag
 * @param <T> 
 */
public class BrushTemplateIO <T extends ElementArea>
        implements FileTypeIOProvider<T> {
    
    private final Supplier<BrushManager<? extends Brush>> supplier;
    private final int defaultBrush;
    private final BiFunction<Integer, Integer, T> areaSupplier;
    
    public BrushTemplateIO(
            Supplier<BrushManager<? extends Brush>> brushManagerSupplier,
            int defaultBrush, BiFunction<Integer, Integer, T> areaSupplier) {
        
        this.supplier = Checker.requireNonNull(brushManagerSupplier);
        this.defaultBrush = defaultBrush;
        this.areaSupplier = areaSupplier;
    }

    @Override
    public String getInfo() {
        return "Šablona v obrázku";
    }

    @Override
    public String getExtension() {
        return "png";
    }

    @Override
    public void save(Path path, T data) throws IOException {
        ImageIO.write(createImage(data), "png", path.toFile());
    }

    protected BufferedImage createImage(T data) {
        BufferedImage bufferedImage = new BufferedImage(
                data.getWidth(), data.getHeight(), BufferedImage.TYPE_INT_ARGB);

        BrushManager<? extends Brush> brushManager = supplier.get();

        data.forEach((element, x, y) -> {
            Brush producer = brushManager.getProducerAll(element);
            int id = (producer == null)
                    ? defaultBrush
                    : producer.getInfo().getId();

            bufferedImage.setRGB(x, y, ~id);
        });
        
        return bufferedImage;
    }
    
    @Override
    public T load(Path path) throws IOException, ParseException {
        return createArea(ImageIO.read(path.toFile()), IntUnaryOperator.identity());
    }
    
    protected T createArea(BufferedImage image, IntUnaryOperator convertor) {
        final BrushManager<? extends Brush> manager = supplier.get();
        final T area = areaSupplier.apply(image.getWidth(), image.getHeight());

        area.forEachPoint((x, y) -> {
            final int id = ~image.getRGB(x, y);
            final Brush brush = manager.getBrush(convertor.applyAsInt(id));

            if (brush != null) {
                Element element = brush.getElement(null, x, y, area, null);
                area.setIfNotNull(x, y, element);
            }
        });
        
        return area;
    }
    
    protected void apply(T area, BufferedImage image, IntUnaryOperator convertor,
            int x, int y) {
        
        final BrushManager<? extends Brush> manager = supplier.get();
        
        final int imgWidth = image.getWidth();
        final int imgHeight = image.getHeight();
        
        final int areaWidth = area.getWidth();
        final int areaHeight = area.getHeight();
        
        for (int cX = x; cX < (x + imgWidth) && cX < areaWidth; cX++) {
            for (int cY = y; cY < (y + imgHeight) && cY < areaHeight; cY++) {

                if (cX < 0 || cY < 0) continue;
                
                final int id = ~image.getRGB(cX - x, cY - y);
                final Brush brush = manager.getBrush(convertor.applyAsInt(id));

                if (brush != null) {
                    Element current = area.get(cX, cY);
                    Element element = brush.getElement(current, cX, cY, area, null);
                    area.setIfNotNull(cX, cY, element);
                }
            }
        }
    }
    
}