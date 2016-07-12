
package cz.hartrik.sg2.app.extension.io;

/**
 * Výjimka vyvolaná při parsování dat.
 *
 * @version 2014-09-20
 * @author Patrik Harag
 */
public class ParseException extends RuntimeException {

    private static final long serialVersionUID = 6549451674104710994L;

    public ParseException() {
        super();
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

}