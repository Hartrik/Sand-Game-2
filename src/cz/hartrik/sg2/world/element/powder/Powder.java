
package cz.hartrik.sg2.world.element.powder;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.FallingElement;
import cz.hartrik.sg2.world.element.solid.GluedPowder;

/**
 * Abstraktní třída pro elementy, které se sypají jako prášek.
 *
 * @version 2014-12-26
 * @author Patrik Harag
 */
public abstract class Powder extends FallingElement implements Glueable {

    private static final long serialVersionUID = 83715083867368_02_068L;

    public Powder(Color color, int density) {
        super(color, density);
    }

    // Glueable

    @Override
    public Element glue() {
        return new GluedPowder<>(this);
    }

}