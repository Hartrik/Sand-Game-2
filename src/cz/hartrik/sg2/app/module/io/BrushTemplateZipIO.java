package cz.hartrik.sg2.app.module.io;

import cz.hartrik.common.Pair;
import cz.hartrik.common.io.NioUtil;
import cz.hartrik.common.reflect.TODO;
import cz.hartrik.sg2.app.module.io.XMLController.Data;
import cz.hartrik.sg2.app.sandbox.Main;
import cz.hartrik.sg2.app.sandbox.element.StandardBrushCollection;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.world.ElementArea;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @version 2015-03-12
 * @author Patrik Harag
 * @param <T> 
 */
@TODO("Podporuje pouze StandardBrushCollection")
public class BrushTemplateZipIO <T extends ElementArea>
        implements FileTypeIOProvider<T> {
    
    public static final String FILE_CONTROLLER = "controller.xml";
    public static final String FILE_DEF_TEMPLATE = "data.png";
    
    private final BrushTemplateIO<T> pngIO;
    
    public BrushTemplateZipIO(
            Supplier<BrushManager<? extends Brush>> brushManagerSupplier,
            int defaultBrush, BiFunction<Integer, Integer, T> areaSupplier) {
        
        this.pngIO = new BrushTemplateIO<>(
                brushManagerSupplier, defaultBrush, areaSupplier);
    }

    @Override
    public String getInfo() {
        return "Šablona";
    }

    @Override
    public String getExtension() {
        return "sgb";
    }

    @Override
    public void save(Path path, T data) throws IOException {
        try (ZipOutputStream zipStream = new ZipOutputStream(
                new BufferedOutputStream(new FileOutputStream(path.toFile())))) {
            
            zipStream.setLevel(9);
            
            // zápis kontroleru
            zipStream.putNextEntry(new ZipEntry(FILE_CONTROLLER));

            Data d = new Data() {{
                title = NioUtil.removeExtension(path.getFileName().toString());
                type = "brush template";
                version = Main.APP_VERSION;
            }};
            
            XMLController.write(zipStream, d, (out) -> {
                out.writeStartElement("brush-manager");
                 out.writeAttribute("version", StandardBrushCollection.getVersion());
                 out.writeCharacters("standard");
                out.writeEndElement();
                out.writeStartElement("file");
                 out.writeCharacters(FILE_DEF_TEMPLATE);
                out.writeEndElement();
            });
            
            // zápis dat
            zipStream.putNextEntry(new ZipEntry(FILE_DEF_TEMPLATE));
            ImageIO.write(pngIO.createImage(data), "png", zipStream);
        }
    }
    
    @Override
    public T load(Path path) throws IOException, ParseException {
        final ZipFile zip = new ZipFile(path.toFile());        
        final ZipEntry controller = findEntry(zip, FILE_CONTROLLER);
        
        final Pair<Data, Node> pair;
        try (InputStream inputStream = zip.getInputStream(controller)) {
            pair = XMLController.read(inputStream);
        }
        
        final NodeList content = pair.getSecond().getChildNodes();
        T area = null;
        IntUnaryOperator operator = IntUnaryOperator.identity();
        
        for (int i = 0; i < content.getLength(); i++) {

            final Node node = content.item(i);
            final String nodeName = node.getNodeName();
            if (nodeName == null) continue;
            
            if (nodeName.equals("file")) {
                String fileName = node.getFirstChild().getNodeValue();
                ZipEntry entry = findEntry(zip, fileName);
                
                Node attX = node.getAttributes().getNamedItem("x");
                Node attY = node.getAttributes().getNamedItem("y");
                
                int x = (attX != null) ? Integer.parseInt(attX.getNodeValue()) : 0;
                int y = (attY != null) ? Integer.parseInt(attY.getNodeValue()) : 0;
                
                try (InputStream inputStream = zip.getInputStream(entry)) {
                    if (area == null)
                        area = pngIO.createArea(ImageIO.read(inputStream), operator);
                    else
                        pngIO.apply(area, ImageIO.read(inputStream), operator, x, y);
                }
            } else if (nodeName.equals("brush-manager")) {
                Node attVersion = node.getAttributes().getNamedItem("version");
                String version = attVersion.getNodeValue();
                operator = StandardBrushCollection.getBCConverter(version);
            }
        }
        
        return area;
    }
    
    private ZipEntry findEntry(ZipFile zip, String fileName) throws IOException {
        return zip.stream()
                .filter(e -> e.getName().equals(fileName))
                .findFirst()
                .orElseThrow(() -> new IOException(fileName + " not found"));
    }
    
}