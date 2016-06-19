package cz.hartrik.sg2.app.module.io;

import cz.hartrik.common.Exceptions;
import java.util.Stack;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Builder, který zjednodušuje tvorbu DOM.
 *
 * @version 2016-06-18
 * @author Patrik Harag
 */
public class SimpleDOM {

    private final Document document;
    private final Element root;

    private final Stack<Element> stack = new Stack<>();

    public SimpleDOM(String rootName) {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = Exceptions.uncheckedGet(
                docFactory::newDocumentBuilder);

		this.document = docBuilder.newDocument();
		this.root = document.createElement(rootName);

        document.appendChild(root);

        stack.push(root);
    }

    public SimpleDOM addAttribute(String key, Object value) {
        stack.peek().setAttribute(key, value.toString());

        return this;
    }

    public SimpleDOM addText(String text) {
        stack.peek().appendChild(document.createTextNode(text));

        return this;
    }

    public SimpleDOM addNode(String name) {
        Element newElement = document.createElement(name);
        stack.peek().appendChild(newElement);
        stack.push(newElement);

        return this;
    }

    public SimpleDOM end() {
        stack.pop();

        return this;
    }

    public Document getDocument() {
        return document;
    }

    public Element getRoot() {
        return root;
    }

    public static SimpleDOM root(String name) {
        return new SimpleDOM(name);
    }

}