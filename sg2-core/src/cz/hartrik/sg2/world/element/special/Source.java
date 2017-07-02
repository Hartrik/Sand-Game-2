package cz.hartrik.sg2.world.element.special;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.random.Chance;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.Air;
import cz.hartrik.sg2.world.element.Metamorphic;
import cz.hartrik.sg2.world.element.SolidElement;
import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Element představující zdroj. Zdroj (source) je statický element, který okolo
 * sebe vytváří další určité elementy.
 *
 * @version 2016-07-02
 * @author Patrik Harag
 */
public class Source extends SolidElement implements Metamorphic<Sourceable> {

    private static final long serialVersionUID = 83715083867368_02_029L;

    private final Color color;

    protected final Supplier<Sourceable> supplier;
    private final Chance chance;

    /**
     * Vytvoří nový zdroj.
     *
     * @param color barva zdroje
     * @param chance šance na vytvoření elementu
     * @param supplier "dodavatel" elementů, musí být {@link Serializable}
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
        tools.getDirVisitor().visitAll(x, y,
                (Element element, int eX, int eY) -> {

            if (element instanceof Air && chance.nextBoolean()) {
                Element newElement = supplier.get();
                if (newElement != null)
                    world.setAndChange(eX, eY, newElement);
            }
        });
    }

    public final Chance getChance() {
        return chance;
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.getDirVisitor()
                .testAll(x, y, element -> element instanceof Air);
    }

    // Metamorphic

    @Override
    public Sourceable getBasicElement() {
        return supplier.get();
    }

}