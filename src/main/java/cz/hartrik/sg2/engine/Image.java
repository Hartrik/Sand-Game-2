package cz.hartrik.sg2.engine;

import cz.hartrik.common.Color;

/**
 * @author Patrik Harag
 * @version 2017-08-06
 */
public interface Image {

    Color getColor(int x, int y);
    void setColor(int x, int y, Color color);

    int getWidth();
    int getHeight();

}
