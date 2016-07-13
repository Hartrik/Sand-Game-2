
package cz.hartrik.sg2.world.element.solid;

import cz.hartrik.common.Color;
import cz.hartrik.sg2.engine.process.Tools;
import cz.hartrik.sg2.world.World;
import cz.hartrik.sg2.world.element.SolidElement;
import cz.hartrik.sg2.world.element.fluid.FluidHeavy;
import cz.hartrik.sg2.world.element.powder.Powder;
import cz.hartrik.sg2.world.element.Metamorphic;
import cz.hartrik.sg2.world.element.special.Sourceable;

/**
 * Element představující určitý roztavený kov.
 *
 * @version 2016-06-17
 * @author Patrik Harag
 */
public class MetalMolten extends FluidHeavy
        implements Meltable, Metamorphic<MetalSolid>, Sourceable {

    private static final long serialVersionUID = 83715083867368_02_058L;

    private final MetalSolid metal;

    MetalMolten(MetalSolid metal) {
        super(metal.getColor(), metal.getDensity());
        this.metal = metal;
    }

    // Element

    @Override
    public void doAction(int x, int y, Tools tools, World world) {
        if (world.getTemperature(x, y) < metal.getMeltingPoint()
                && findBase(x, y, tools, world)) {

            // změna zpět do pevného skupenství
            world.setAndChange(x, y, metal);

        } else {
            // míchání barev mezi roztavenými kovy
            if (tools.randomInt(10) != 0)
                colorBlending(x, y, tools, world);

            // bude "téct"
            super.doAction(x, y, tools, world);
        }
    }

    protected boolean findBase(int x, int y, Tools tools, World world) {
        if (y == world.getHeight() - 1)
            return true;

        return !tools.getDirVisitor().visitWhileAll(x, y, (element, ex, ey) -> {
            if (element instanceof SolidElement)
                return false;

            // kov by něměl moc často ztuhnout ve vzduchu
            // problém jsou plameny, které ho mohou při padání výrazně ochladit
            return !(element instanceof Powder
                    && world.getTemperature(ex, ey) < getMeltingPoint() / 4);
        });
    }

    protected void colorBlending(int x, int y, Tools tools, World world) {
        tools.getDirVisitor().visit(x, y, (element, ex, ey) -> {
            if (element instanceof MetalMolten) {
                MetalSolid metal2 = ((MetalMolten) element).getBasicElement();

                final Color c1 = metal2.getColor();
                final Color c2 = metal.getColor();

                int diff = Math.abs(c1.getRed()   - c2.getRed())
                         + Math.abs(c1.getGreen() - c2.getGreen())
                         + Math.abs(c1.getBlue()  - c2.getBlue());

                if (diff < 6) return;

                Color newColor = c1.changeAlpha(.4).blend(c2.changeAlpha(.4));

                MetalSolid m1 = metal.newInstance(newColor);
                MetalSolid m2 = metal2.newInstance(newColor);

                world.set(x, y, m1.melt());
                world.set(ex, ey, m2.melt());
            }

        }, tools.randomDirection());
    }

    @Override
    public boolean testAction(int x, int y, Tools tools, World world) {
        return tools.testTemperature(x, y)
                || super.testAction(x, y, tools, world);
    }

    @Override
    public Color getColor(float temperature) {
        return metal.getColor(temperature);
    }

    @Override
    public boolean hasTemperature() {
        return true;
    }

    @Override
    public boolean isConductive() {
        return true;  // všechny kovy vedou teplo
    }

    @Override
    public float getConductiveIndex() {
        return metal.getConductiveIndex();
    }

    @Override
    public float loss() {
        return metal.loss();
    }

    // Metamorphic

    @Override
    public MetalSolid getBasicElement() {
        return metal;
    }

    // Meltable

    @Override
    public int getMeltingPoint() {
        return metal.getMeltingPoint();
    }

    @Override
    public final boolean isMolten() {
        return true;
    }

}