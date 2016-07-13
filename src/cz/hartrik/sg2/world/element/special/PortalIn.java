package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.Direction;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.NonSolidElement;
import java.util.function.Supplier;

/**
 * Element představující vstupní portál.
 *
 * @version 2016-06-15
 * @author Patrik Harag
 */
public class PortalIn extends Portal {

    private static final long serialVersionUID = 83715083867368_02_088L;

    public PortalIn(Supplier<Color> supplier) {
        super(supplier);
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        final Direction direction = tools.randomDirection();

        final int ex = x + direction.getX();
        final int ey = y + direction.getY();

        if (world.valid(ex, ey)) {
            final Element element = world.get(ex, ey);

            if (!(element instanceof NonSolidElement) || element instanceof Air)
                return;

            final int dx = -direction.getX();
            final int dy = -direction.getY();
            int nx = x + dx;
            int ny = y + dy;

            boolean outputFound = false;

            while (world.valid(nx, ny)) {
                final Element next = world.get(nx, ny);

                if (outputFound) {
                    if (next instanceof Air) {
                        transfer(ex, ey, element, nx, ny, world);
                        return;

                    } else if (!(next instanceof PortalOut)) {
                        // přes tento výstup nelze projít
                        // pokud bychom chtěli pokračovat v hledání, tak
                        // stačí nastavit outputFound = false
                        return;
                    }

                } else if (next instanceof PortalOut) {
                    // nalezen výstup
                    outputFound = true;
                }

                nx += dx;
                ny += dy;
            }
        }
    }

    private void transfer(int ex, int ey, Element e, int nx, int ny, World world) {
        world.setAndChange(ex, ey, world.getBackground());
        world.setAndChange(nx, ny, e);

        float temperature = world.getTemperature(ex, ey);
        world.setTemperature(nx, ny, temperature);
        world.setTemperature(ex, ey, World.DEFAULT_TEMPERATURE);
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getDirVisitor().testAll(x, y, (element) ->
                element instanceof NonSolidElement && !(element instanceof Air));
    }

}