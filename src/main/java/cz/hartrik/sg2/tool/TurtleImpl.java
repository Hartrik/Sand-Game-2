package cz.hartrik.sg2.tool;

import cz.hartrik.common.Pair;
import cz.hartrik.common.Point;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.world.BrushInserter;
import cz.hartrik.sg2.world.ChunkedArea;
import cz.hartrik.sg2.world.ElementArea;
import java.util.function.Consumer;

/**
 * @version 2015-03-18
 * @author Patrik Harag
 */
public class TurtleImpl implements Turtle {
    
    private double angle = DEFAULT_ANGLE;
    private double x, y;
    
    private final ElementArea area;
    private Brush brush;
    private Draggable tool;

    public TurtleImpl(ElementArea elementArea, Brush brush, Draggable tool) {
        this.area = elementArea;
        this.brush = brush;
        this.tool = tool;
    }
    
    // želvý API
    
    // --- relativní
    
    @Override
    public TurtleImpl draw(double length) {
        final Pair<Double, Double> dest = countMove(length);
        
        Point from = round(x, y);
        Point to = round(dest.getFirst(), dest.getSecond());
        
        tool.stroke(from, to, getInserter());
        x = dest.getFirst(); y = dest.getSecond();
        return this;
    }
    
    @Override
    public TurtleImpl move(double length) {
        final Pair<Double, Double> dest = countMove(length);
        x = dest.getFirst(); y = dest.getSecond();
        return this;
    }
    
    @Override
    public TurtleImpl point() {
        tool.apply(round(x, y), getInserter());
        return this;
    }
    
    // --- absolutní

    @Override
    public TurtleImpl setX(double x) { this.x = x; return this; }
    @Override
    public TurtleImpl setY(double y) { this.y = y; return this; }

    @Override
    public double getX() { return x; }
    @Override
    public double getY() { return y; }

    @Override
    public Turtle center() {
        setX(area.getWidth() / 2);
        setY(area.getHeight() / 2);
        return this;
    }
    
    @Override
    public TurtleImpl setAngle(double a) { angle = a % 360; return this; }
    @Override
    public double getAngle() { return angle; }

    @Override
    public TurtleImpl setBrush(Brush brush) {
        this.brush = brush;
        return this;
    }

    @Override
    public TurtleImpl setTool(Draggable tool) {
        this.tool = tool;
        return this;
    }

    // speciální
    
    @Override
    public Turtle repeat(int i) {
        return new TurtleNested(this, (instructions) -> {
            if (instructions.isEmpty()) return;
            for (int j = 0; j < i; j++)
                for (Consumer<Turtle> instruction : instructions)
                    instruction.accept(this);
        });
    }

    @Override
    public Turtle end() {
        if (area instanceof ChunkedArea)
            ((ChunkedArea) area).setAllChunks(true);
        
        return null;
    }

    @Override
    public Turtle run(Consumer<Turtle> procedure) {
        procedure.accept(this);
        return this;
    }
    
    // pomocné metody
    
    private Pair<Double, Double> countMove(double length) {
        final double a = angle - 90; // jinak by bylo 0° vpravo
        final double nx = x + length * Math.cos(a * Math.PI / 180);
        final double ny = y + length * Math.sin(a * Math.PI / 180);
        return Pair.of(nx, ny);
    }
    
    private BrushInserter<?> getInserter() {
        return area.getInserter().with(brush);
    }
    
    private Point round(double x, double y) {
        return Point.of((int) Math.round(x), (int) Math.round(y));
    }
    
}