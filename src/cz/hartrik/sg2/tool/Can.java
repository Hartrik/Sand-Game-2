package cz.hartrik.sg2.tool;

import cz.hartrik.common.Point;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.manage.BrushManager;
import cz.hartrik.sg2.world.BrushInserter;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.element.Elements;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @version 2015-03-21
 * @author Patrik Harag
 */
public class Can implements Tool {

    private final Supplier<Brush> replacementSupplier;
    private final BrushManager brushManager;

    public Can(Supplier<Brush> replacement, BrushManager brushManager) {
        this.replacementSupplier = replacement;
        this.brushManager = brushManager;
    }

    @Override
    public void apply(int x, int y, BrushInserter<?> inserter) {
        final ElementArea area = inserter.getArea();
        if (!area.valid(x, y)) return;

        Element element = area.get(x, y);
        Element base = Elements.getBaseElement(element).orElse(element);

        final Brush target = brushManager.getProducer(base);
        final Brush replacement = replacementSupplier.get();

        if (target == null || replacement == null) {
//            System.out.println("base = " + base);
//            System.out.println("target = " + target);
//            System.out.println("replacement = " + replacement);
            return;
        }

        FloodFill floodFill = new FloodFill(area, inserter, target, replacement);
        floodFill.fill(Point.of(x, y));

        inserter.finalizeInsertion();
    }

    private static class FloodFill {

        private final ElementArea area;
        private final BrushInserter<?> inserter;
        private final Brush target;
        private final Brush replacement;

        public FloodFill(ElementArea area, BrushInserter<?> inserter,
                Brush target, Brush replacement) {

            this.area = area;
            this.inserter = inserter;
            this.target = target;
            this.replacement = replacement;
        }

        public void fill(Point point) {
            if (target == replacement) return;

            final int width = area.getWidth();
            final int height = area.getHeight();

            final Set<Point> pointSet = new LinkedHashSet<>();
            final Deque<Point> queue = new LinkedList<>();

            do {
                int x = point.getX();
                int y = point.getY();

                while (x > 0 && equals(x - 1, y)) {
                    x--;
                }

                boolean spanUp = false;
                boolean spanDown = false;

                while (x < width && equals(x, y)) {

                    Point nextPoint = Point.of(x, y);
                    if (pointSet.contains(nextPoint))
                        break;

                    inserter.apply(x, y, replacement);
                    pointSet.add(nextPoint);

                    if (!spanUp && y > 0 && equals(x, y - 1)) {
                        queue.add(new Point(x, y - 1));
                        spanUp = true;
                    } else if (spanUp && y > 0 && equals(x, y - 1)) {
                        spanUp = false;
                    }

                    if (!spanDown && y < height - 1 && equals(x, y + 1)) {
                        queue.add(new Point(x, y + 1));
                        spanDown = true;
                    } else if (spanDown && y < height - 1 && equals(x, y + 1)) {
                        spanDown = false;
                    }

                    x++;
                }
            } while ((point = queue.pollFirst()) != null);
        }

        private boolean equals(int x, int y) {
            final Element element = area.get(x, y);

            if (target.produces(element))
                return true;

            Optional<Element> baseElement = Elements.getBaseElement(element);
            if (baseElement.isPresent())
                return target.produces(baseElement.get());

            return false;
        }
    }

}