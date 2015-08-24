
package cz.hartrik.sg2.app.module.io;

import cz.hartrik.common.Pair;
import cz.hartrik.common.io.NioUtil;
import cz.hartrik.sg2.app.module.io.XMLController.Data;
import cz.hartrik.sg2.app.sandbox.Main;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Zprostředkuje io operace pomocí serializace.
 * 
 * @version 2015-03-12
 * @author Patrik Harag
 * @param <T> vstup/výstup
 */
public class SerZipIO<T> implements FileTypeIOProvider<T> {

    public static final String FILE_CONTROLLER = "controller.xml";
    public static final String FILE_DEF_TEMPLATE = "data.ser";
    
    private final SerIO<T> serIO;
    
    public SerZipIO() {
        this.serIO = new SerIO<>();
    }
    
    @Override
    public String getInfo() {
        return "Serializovaná data";
    }
    
    @Override
    public String getExtension() {
        return "sgs";
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
                type = "serialized canvas";
                version = Main.APP_VERSION;
            }};
            
            XMLController.write(zipStream, d, (out) -> {
                out.writeStartElement("file");
                 out.writeCharacters(FILE_DEF_TEMPLATE);
                out.writeEndElement();
            });
            
            // zápis dat
            zipStream.putNextEntry(new ZipEntry(FILE_DEF_TEMPLATE));
            serIO.writeData(data, zipStream);
        }
    }

    @Override
    public T load(Path path) throws IOException {
        final ZipFile zip = new ZipFile(path.toFile());
        final ZipEntry controller = findEntry(zip, FILE_CONTROLLER);
        
        Pair<Data, Node> pair;
        try (InputStream inputStream = zip.getInputStream(controller)) {
            pair = XMLController.read(inputStream);
        }
        
        NodeList content = pair.getSecond().getChildNodes();
        
        for (int i = 0; i < content.getLength(); i++) {

            Node node = content.item(i);
            String nodeName = node.getNodeName();
            if (nodeName == null) continue;
            
            if (nodeName.equals("file")) {
                String fileName = node.getFirstChild().getNodeValue();
                ZipEntry entry = findEntry(zip, fileName);
                
                try (InputStream inputStream = zip.getInputStream(entry)) {
                    try {
                        return serIO.load(inputStream);
                    } catch (Exception ex) {
                        throw new IOException(ex);
                    }
                }
            }
        }
        return null;
    }

    private ZipEntry findEntry(ZipFile zip, String fileName) throws IOException {
        return zip.stream()
                .filter(e -> e.getName().equals(fileName))
                .findFirst()
                .orElseThrow(() -> new IOException(fileName + " not found"));
    }
    
}