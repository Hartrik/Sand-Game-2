package cz.hartrik.sg2.app.extension.io;

import cz.hartrik.common.Point;
import cz.hartrik.common.reflect.TODO;
import cz.hartrik.sg2.app.sandbox.element.StandardBrushCollection;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.Inserter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import javax.imageio.ImageIO;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ID štětců uložená v png obrázku.
 *
 * @version 2016-06-20
 * @author Patrik Harag
 */
@TODO("Podporuje pouze StandardBrushCollection")
public class ResourceBrushTemplate implements ResourceType {

    public static final String IDENTIFIER = "brush template";

    private final Supplier<BrushManager> supplier;
    private final int defaultBrushID;

    public ResourceBrushTemplate(
            Supplier<BrushManager> supplier, int defaultBrushID) {

        this.supplier = supplier;
        this.defaultBrushID = defaultBrushID;
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    // --- zápis

    @Override
    public void writeXML(SimpleDOM dom) {
        dom.addNode("brush-manager")
                .addAttribute("version", StandardBrushCollection.getVersion())
                .addText("standard")
                .end();
    }

    @Override
    public void writeData(OutputStream os, ElementArea area) throws IOException {
        ImageIO.write(createImage(area, supplier.get()), "png", os);
    }

    protected BufferedImage createImage(ElementArea area, BrushManager bm) {
        BufferedImage bufferedImage = new BufferedImage(
                area.getWidth(), area.getHeight(), BufferedImage.TYPE_INT_ARGB);

        area.forEach((element, x, y) -> {
            int id = getID(element, bm, defaultBrushID);
            bufferedImage.setRGB(x, y, ~id);
        });

        return bufferedImage;
    }

    protected int getID(Element element, BrushManager brushManager, int def) {
        Brush producer = brushManager.getProducerAll(element);
        return (producer == null)
                ? def
                : producer.getInfo().getId();
    }

    // --- načtení

    @Override
    public void loadData(InputStream inputStream, Node node, ElementArea area)
            throws IOException {

        BufferedImage image = ImageIO.read(inputStream);
        IntUnaryOperator mapper = parseMapper(node);
        Point position = ParseUtils.parsePosition(node);

        apply(area, image, mapper, supplier.get(), position.getX(), position.getY());
    }

    private IntUnaryOperator parseMapper(Node node) {
        NodeList nodes = node.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node next = nodes.item(i);
            String nodeName = next.getNodeName();

            if ("brush-manager".equals(nodeName)) {
                Node attVersion = next.getAttributes().getNamedItem("version");
                String version = attVersion.getNodeValue();
                return StandardBrushCollection.getBCConverter(version);
            }
        }

        return IntUnaryOperator.identity();
    }

    protected void apply(ElementArea area, BufferedImage image, IntUnaryOperator convertor,
            BrushManager manager, int x, int y) {

        final int imgWidth = image.getWidth();
        final int imgHeight = image.getHeight();

        final int areaWidth = area.getWidth();
        final int areaHeight = area.getHeight();

        Inserter<?> inserter = area.getInserter();
        inserter.setEraseTemperature(false);

        for (int cX = x; cX < (x + imgWidth) && cX < areaWidth; cX++) {
            for (int cY = y; cY < (y + imgHeight) && cY < areaHeight; cY++) {

                if (cX < 0 || cY < 0) continue;

                final int id = ~image.getRGB(cX - x, cY - y);
                final Brush brush = manager.getBrush(convertor.applyAsInt(id));

                if (brush != null) {
                    inserter.apply(cX, cY, brush);
                }
            }
        }
    }

}