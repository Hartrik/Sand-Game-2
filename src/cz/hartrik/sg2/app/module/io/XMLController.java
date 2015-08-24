package cz.hartrik.sg2.app.module.io;

import cz.hartrik.common.Pair;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @version 2015-03-12
 * @author Patrik Harag
 */
class XMLController {
    
    static class Data {
        public String type = "";
        public String version = "";
        public String title = "";
        public String description = "";
    }
    
    // write
    
    static void write(
            OutputStream outStream, Data data, XMLConsumer consumer)
            throws IOException {
        
        try {
            writeImpl(outStream, data, consumer);
        } catch (TransformerException | XMLStreamException ex) {
            throw new IOException(ex);
        }
    }
    
    private static void writeImpl(
            OutputStream outStream, Data data, XMLConsumer consumer)
            throws IOException, XMLStreamException, TransformerException {
        
        StringWriter stringWriter = new StringWriter();

        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter out = factory.createXMLStreamWriter(stringWriter);

        out.writeStartElement("data");
         out.writeAttribute("type", data.type);
         out.writeAttribute("app-version", data.version);

         out.writeStartElement("title");
          out.writeCharacters(data.title);
         out.writeEndElement();

         out.writeStartElement("description");
          out.writeCharacters(data.description);
         out.writeEndElement();

         out.writeStartElement("content");
          consumer.accept(out);
         out.writeEndElement();
        out.writeEndElement();
        out.close();

        Source xmlInput = new StreamSource(new StringReader(stringWriter.toString()));
        StreamResult xmlOutput = new StreamResult(new OutputStreamWriter(outStream, "UTF-8"));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", 2);
        Transformer transformer = transformerFactory.newTransformer(); 
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(xmlInput, xmlOutput);
    }
    
    static interface XMLConsumer {
        public void accept(XMLStreamWriter out) throws XMLStreamException;
    }
    
    // read
    
    static Pair<Data, Node> read(InputStream inputStream)
            throws ParseException, IOException {
        
        final Document document;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(inputStream);
            document.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException ex) {
            throw new ParseException(ex);
        }

        // získání obecných informací
        
        Element documentElement = document.getDocumentElement();
        
        Data data = new Data() {{
            type = documentElement.getAttribute("type");
            version = documentElement.getAttribute("app-version");
            title = "not implemented";
            description = title;
        }};
        
        // získání uzlu se specifikacemi
        
        NodeList contentList = document.getElementsByTagName("content");
        if (contentList.getLength() < 1)
            throw new ParseException("xml controller - there is no <content>");

        // návratová hodnota
        
        return Pair.of(data, contentList.item(0));
    }
    
}