
package cz.hartrik.sg2.io.zip.resource;

import cz.hartrik.common.Point;
import cz.hartrik.sg2.io.zip.ParseUtils;
import cz.hartrik.sg2.io.zip.SimpleDOM;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.Inserter;
import cz.hartrik.sg2.world.template.ElementAreaTemplate;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import org.w3c.dom.Node;

/**
 * Serializované plátno.
 *
 * @version 2017-08-06
 * @author Patrik Harag
 */
public class ResourceSerialized implements ResourceType {

    public static final String IDENTIFIER = "SERIALIZED";

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public void writeData(OutputStream out, ElementArea data) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(data);
        }
    }

    @Override
    public void writeXML(SimpleDOM dom) {
        // není potřeba
    }

    @Override
    public void loadData(InputStream in, Node node, ElementArea area)
            throws IOException {

        ElementArea loaded;

        try (ObjectInputStream iis = new ObjectInputStream(in)) {

            @SuppressWarnings("unchecked")
            ElementArea result = (ElementArea) iis.readObject();
            loaded = result;

        } catch (ClassNotFoundException ex) {
            throw new IOException(ex);
        }

        Point position = ParseUtils.parsePosition(node);

        insert(loaded, area, position.getX(), position.getY());
    }

    private void insert(ElementArea from, ElementArea to, int x, int y) {
        ElementAreaTemplate template = new ElementAreaTemplate(from);

        Inserter<?> inserter = to.getInserter();
        inserter.setEraseTemperature(false);

        template.insert(inserter, x, y);
    }

}