
package cz.hartrik.sg2.app.extension.io;

import cz.hartrik.sg2.world.ElementArea;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.w3c.dom.Node;

/**
 * Spravuje ukládání a načítání nějakého druhu dat vztahující se k
 * {@link ElementArea}.
 *
 * @version 2016-06-18
 * @author Patrik Harag
 */
public interface ResourceType {

    /**
     * Vrátí unikátní název tohoto typu dat.
     * Slouží hlavně pro následnou identifikaci
     *
     * @return identifikátor
     */
    String getIdentifier();

    /**
     * Zapíše data do výstupního proudu.
     *
     * @param os výstupní proud, do kterého se zapíše resource
     * @param area plátno
     * @throws IOException
     */
    void writeData(OutputStream os, ElementArea area) throws IOException;

    /**
     * Zapíše podrobnosti o datech zapisovaných metodou
     * {@link #writeData(OutputStream, ElementArea)}.
     * Takto zapsaná data slouží při načítání v metodě
     * {@link #loadData(InputStream, Node, ElementArea)}.
     *
     * @param dom DOM builder
     */
    void writeXML(SimpleDOM dom);

    /**
     * Načte data.
     *
     * @param is vstupní proud příslušného resource
     * @param node uzel {@code resource} v DOM
     * @param area plátno do kterého se načtená data vloží
     * @throws IOException
     */
    void loadData(InputStream is, Node node, ElementArea area) throws IOException;

}