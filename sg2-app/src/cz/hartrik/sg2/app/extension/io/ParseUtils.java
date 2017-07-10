
package cz.hartrik.sg2.app.extension.io;

import cz.hartrik.common.Point;
import cz.hartrik.sg2.world.ElementArea;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 *
 * @version 2016-06-21
 * @author Patrik Harag
 */
public class ParseUtils {

    private ParseUtils() { }

    public static Point parsePosition(Node resourceNode) {
        NodeList childNodes = resourceNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {

            Node node = childNodes.item(i);
            String nodeName = node.getNodeName();

            if (nodeName != null && nodeName.equals("position")) {
                Node attX = node.getAttributes().getNamedItem("x");
                Node attY = node.getAttributes().getNamedItem("y");

                try {
                    int x = Integer.parseInt(attX.getNodeValue());
                    int y = Integer.parseInt(attY.getNodeValue());
                    return Point.of(x, y);

                } catch (NullPointerException | NumberFormatException e) {
                    throw new ParseException(e);
                }
            }
        }
        return Point.of(0, 0);
    }

    public static <T extends ElementArea> T parseCanvas(
            Node contentNode, ElementAreaProvider<T> areaProvider) {

        Node attW = contentNode.getAttributes().getNamedItem("width");
        Node attH = contentNode.getAttributes().getNamedItem("height");
        Node attS = contentNode.getAttributes().getNamedItem("chunk-size");

        int width, height, chunkSize;

        try {
            width = Integer.parseInt(attW.getNodeValue());
            height = Integer.parseInt(attH.getNodeValue());
        } catch (Exception e) {
            throw new ParseException("Unknown canvas size");
        }

        try {
            chunkSize = Integer.parseInt(attS.getNodeValue());
        } catch (Exception e) {
            throw new ParseException("Unknown chunk size");
        }

        try {
            return areaProvider.create(width, height, chunkSize);
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }

}