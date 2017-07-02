package cz.hartrik.sg2.app.sandbox.element;

import cz.hartrik.sg2.brush.manage.BrushInfo;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.flora.Grass;
import java.util.function.IntUnaryOperator;

/**
 * @version 2015-02-13
 * @author Patrik Harag
 */
public class JFXSeedBrush extends JFXGrassBrush {

    private final Grass[] grass;

    public JFXSeedBrush(BrushInfo info, Grass[] grass,
            IntUnaryOperator operator, Element... elements) {

        super(info, operator, elements);
        this.grass = grass;
    }

    @Override
    public boolean produces(Element element) {
        if (super.produces(element))
            return true;

        for (Grass next : grass)
            if (next.getClass() == element.getClass())
                return true;

        return false;
    }

}