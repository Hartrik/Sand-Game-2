package cz.hartrik.sg2.app.extension.io;

import java.io.*;
import java.util.function.Consumer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Poskytuje metody pro vytváření a načítání XML kontroleru, který řídí načítání
 * resources ze zip souboru.
 *
 * @version 2016-06-21
 * @author Patrik Harag
 */
class XMLController {

    private XMLController() { }

    static class Data {
        public String appName = "";
        public String appVersion = "";

        public String title = "";
        public String description = "";

        public int contentWidth;
        public int contentHeight;
        public int chunkSize;
    }

    // write

    static void write(OutputStream out, Data data, Consumer<SimpleDOM> content)
            throws IOException {

        try {
            writeImpl(out, data, content);
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    private static void writeImpl(
            OutputStream outStream, Data data, Consumer<SimpleDOM> contentProvider)
            throws IOException, XMLStreamException,
                   TransformerException, ParserConfigurationException {

        SimpleDOM dom = SimpleDOM
            .root("data")
                .addAttribute("app-name", data.appName)
                .addAttribute("app-version", data.appVersion)
                .addNode("title").addText(data.title).end()
                .addNode("description").addText(data.description).end()
                .addNode("content")
                    .addAttribute("width", data.contentWidth)
                    .addAttribute("height", data.contentHeight)
                    .addAttribute("chunk-size", data.chunkSize);

        contentProvider.accept(dom);

        DOMSource source = new DOMSource(dom.getDocument());
		StreamResult result = new StreamResult(new OutputStreamWriter(outStream, "UTF-8"));

        Transformer transformer = createTransformer();
		transformer.transform(source, result);
	}

    private static Transformer createTransformer() throws TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        return transformer;
    }

    // read

    static Node read(InputStream inputStream)
            throws ParseException, IOException {

        Document document = loadDocument(inputStream);
        NodeList contentList = document.getElementsByTagName("content");

        if (contentList.getLength() < 1)
            throw new ParseException("xml controller - there is no <content>");

        Node contentNode = contentList.item(0);

        return contentNode;
    }

    private static Document loadDocument(InputStream inputStream) throws IOException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(inputStream);
            document.getDocumentElement().normalize();
            return document;

        } catch (ParserConfigurationException | SAXException ex) {
            throw new ParseException(ex);
        }
    }

}