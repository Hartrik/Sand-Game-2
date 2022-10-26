package cz.hartrik.sg2.engine;

import cz.hartrik.sg2.world.ElementArea;

/**
 * @author Patrik Harag
 * @version 2017-08-06
 */
public interface PlatformProvider {

    Image createImage(int w, int h);
    Image createImage(String url);
    Image renderPreview(ElementArea area);

}
