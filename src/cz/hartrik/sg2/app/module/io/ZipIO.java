package cz.hartrik.sg2.app.module.io;

import cz.hartrik.common.Exceptions;
import cz.hartrik.common.io.NioUtil;
import cz.hartrik.sg2.app.sandbox.Main;
import cz.hartrik.sg2.world.ChunkedArea;
import cz.hartrik.sg2.world.ElementArea;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Abstraktní třída pro I/O do ZIP souboru.
 *
 * @version 2016-06-21
 * @author Patrik Harag
 * @param <T>
 */
public abstract class ZipIO<T extends ElementArea>
        implements FileTypeIOProvider<T> {

    public static final String FILE_CONTROLLER = "controller.xml";

    protected final ElementAreaProvider<T> areaProvider;
    protected final ResourceTypeManager resourceTypeManager;

    public ZipIO(ElementAreaProvider<T> areaProvider,
            ResourceTypeManager resourceTypeManager) {

        this.areaProvider = areaProvider;
        this.resourceTypeManager = resourceTypeManager;
    }

    public Collection<ResourceType> getSupportedResourceTypes() {
        return resourceTypeManager.getAllSupported();
    }

    public abstract Map<String, ResourceType> getWriteResourceTypes();

    @Override
    public void save(Path path, T data) throws IOException {
        try (ZipOutputStream zipStream = new ZipOutputStream(
                new BufferedOutputStream(new FileOutputStream(path.toFile())))) {

            zipStream.setLevel(9);

            // zápis kontroleru

            zipStream.putNextEntry(new ZipEntry(FILE_CONTROLLER));

            XMLController.Data d = new XMLController.Data() {{
                appName = Main.APP_NAME;
                appVersion = Main.APP_VERSION;
                title = NioUtil.removeExtension(path.getFileName().toString());
                description = "";
                contentWidth = data.getWidth();
                contentHeight = data.getHeight();
                chunkSize = (data instanceof ChunkedArea)
                        ? ((ChunkedArea) data).getChunkSize() : -1;
            }};

            Map<String, ResourceType> resources = getWriteResourceTypes();

            XMLController.write(zipStream, d, (xmlBuilder) -> {
                resources.entrySet().forEach(pair -> {
                    ResourceType resource = pair.getValue();
                    String resourceName = pair.getKey();

                    xmlBuilder
                        .addNode("resource")
                            .addAttribute("name", resourceName)
                            .addAttribute("type", resource.getIdentifier());

                    resource.writeXML(xmlBuilder);

                    xmlBuilder.end();
                });
            });

            // zápis resources

            resources.entrySet().forEach(pair -> {
                Exceptions.unchecked(() -> {
                    ResourceType resource = pair.getValue();
                    String resourceName = pair.getKey();

                    zipStream.putNextEntry(new ZipEntry(resourceName));
                    resource.writeData(zipStream, data);
                });
            });
        }
    }

    @Override
    public T load(Path path) throws IOException, ParseException {
        try (ZipFile zip = new ZipFile(path.toFile())) {
            ZipEntry controller = findEntry(zip, FILE_CONTROLLER);

            Node contentNode;
            try (InputStream inputStream = zip.getInputStream(controller)) {
                contentNode = XMLController.read(inputStream);
            }

            T area = ParseUtils.parseCanvas(contentNode, areaProvider);

            NodeList content = contentNode.getChildNodes();
            for (int i = 0; i < content.getLength(); i++) {

                Node node = content.item(i);
                String nodeName = node.getNodeName();

                if (nodeName != null && nodeName.equals("resource")) {
                    loadResource(area, node, zip);
                }
            }
            return area;
        }
    }

    private void loadResource(T area, Node resourceNode, ZipFile zip)
            throws IOException {

        NamedNodeMap attributes = resourceNode.getAttributes();
        String fileName = attributes.getNamedItem("name").getNodeValue();
        String type = attributes.getNamedItem("type").getNodeValue();

        ResourceType resourceType = findResourceType(type);
        ZipEntry entry = findEntry(zip, fileName);

        try (InputStream inputStream = zip.getInputStream(entry)) {
            resourceType.loadData(inputStream, resourceNode, area);
        }
    }

    private ResourceType findResourceType(String id) throws IOException {
        return getSupportedResourceTypes().stream()
                .filter(r -> r.getIdentifier().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new IOException("Unsupported resource type - " + id));
    }

    private ZipEntry findEntry(ZipFile zip, String fileName) throws IOException {
        return zip.stream()
                .filter(e -> e.getName().equals(fileName))
                .findFirst()
                .orElseThrow(() -> new IOException(fileName + " not found"));
    }

}
