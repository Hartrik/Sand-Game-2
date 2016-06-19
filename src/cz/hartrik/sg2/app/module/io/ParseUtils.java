package cz.hartrik.sg2.app.module.io;

import cz.hartrik.common.Pair;
import cz.hartrik.common.Point;
import cz.hartrik.sg2.world.Chunk;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 *
 * @version 2016-06-19
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

    public static Pair<Integer, Integer> parseCanvasSize(Node contentNode) {
        Node attW = contentNode.getAttributes().getNamedItem("width");
        Node attH = contentNode.getAttributes().getNamedItem("height");

        int width, height;

        try {
            width = Integer.parseInt(attW.getNodeValue());
            height = Integer.parseInt(attH.getNodeValue());

        } catch (Exception e) {
            throw new ParseException("Unknown canvas size");
        }

        if (width <= 0 || height <= 0)
            throw new ParseException("Canvas size cannot be zero or negative");

        if (width % Chunk.CHUNK_SIZE != 0 || height % Chunk.CHUNK_SIZE != 0)
            throw new ParseException("Wrong canvas size");

        return Pair.of(width, height);
    }

}