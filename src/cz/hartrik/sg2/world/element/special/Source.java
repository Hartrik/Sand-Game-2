package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.common.random.Chance;
import cz.hartrik.sg2.process.Tools;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.type.Sourceable;
import java.util.function.Supplier;

/**
 * Element představující zdroj. Zdroj (source) je statický element, který okolo
 * sebe vytváří další určité elementy.
 *
 * @version 2014-08-21
 * @author Patrik Harag
 */
public class Source extends SolidElement {

    private static final long serialVersionUID = 83715083867368_02_029L;

    private final Color color;

    protected final Supplier<Sourceable> supplier;
    private final Chance chance;

    /**
     * Vytvoří nový zdroj.
     *
     * @param color barva zdroje
     * @param chance šance na vytvoření elementu
     * @param supplier "dodavatel" elementů
     */
    public Source(Color color, Chance chance, Supplier<Sourceable> supplier) {
        this.color = color;
        this.chance = chance;
        this.supplier = supplier;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        tools.getDirectionVisitor().visitAll(x, y,
                (Element element, int eX, int eY) -> {

            if (element instanceof Air && chance.nextBoolean()) {
                world.setAndChange(eX, eY, supplier.get());
            }
        });
    }

    public final Chance getChance() {
        return chance;
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getDirectionVisitor()
                .testAll(x, y, element -> element instanceof Air);
    }

}