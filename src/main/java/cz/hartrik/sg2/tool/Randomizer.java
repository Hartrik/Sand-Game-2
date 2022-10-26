package cz.hartrik.sg2.tool;

import cz.hartrik.sg2.random.XORShiftRandom;
import cz.hartrik.sg2.world.BrushInserter;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.Inserter;

/**
 * Nástroj, který náhodně promíchává elementy.
 *
 * @version 2016-06-16
 * @author Patrik Harag
 */
public class Randomizer implements Tool, Fillable {

    private final Shape shape;

    public Randomizer(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void apply(int x, int y, BrushInserter<?> inserter) {
        randomize(x, y, shape, inserter);
    }

    @Override
    public void apply(int x, int y, int w, int h, BrushInserter<?> inserter) {
        randomize(x, y, new Rectangle(w, h), inserter);
    }

    private void randomize(int sX, int sY, Shape shape, Inserter<?> inserter) {
        inserter.setEraseTemperature(false);

        final XORShiftRandom random = new XORShiftRandom();
        final ElementArea area = inserter.getArea();

        area.take(shape, sX, sY).forEachPoint((x1, y1) -> {
            final int x2 = x1 + random.nextInt(3) - 1;
            final int y2 = y1 + random.nextInt(3) - 1;

            if (area.valid(x2, y2)) {
                swap(x1, y1, x2, y2, area, inserter);
            }
        });
    }

    private void swap(int x1, int y1, int x2, int y2,
            ElementArea area, Inserter<?> inserter) {

        final Element e1 = area.get(x1, y1);
        final Element e2 = area.get(x2, y2);

        area.set(x1, y1, e2);
        area.set(x2, y2, e1);

        final float temp1 = area.getTemperature(x1, y1);
        final float temp2 = area.getTemperature(x2, y2);
        area.setTemperature(x1, y1, temp2);
        area.setTemperature(x2, y2, temp1);

        // používá inserter pro probuzení chunků
        inserter.insert(x1, y1, e2);
        inserter.insert(x2, y2, e1);
    }

    public Shape getShape() {
        return shape;
    }

}