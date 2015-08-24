package cz.hartrik.sg2.tool;

import cz.hartrik.sg2.brush.Brush;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @version 2015-03-18
 * @author Patrik Harag
 */
public class TurtleNested implements Turtle {

    private final Turtle turtle;
    private final Consumer<List<Consumer<Turtle>>> consumer;
    
    private final List<Consumer<Turtle>> instructions;

    public TurtleNested(
            Turtle turtle, Consumer<List<Consumer<Turtle>>> consumer) {
        
        this.turtle = turtle;
        this.consumer = consumer;
        this.instructions = new LinkedList<>();
    }
    
    // metody

    @Override
    public Turtle move(double length) {
        instructions.add(t -> t.move(length));
        return this;
    }

    @Override
    public Turtle draw(double length) {
        instructions.add(t -> t.draw(length));
        return this;
    }

    @Override
    public Turtle point() {
        instructions.add(Turtle::point);
        return this;
    }

    @Override
    public Turtle setX(double x) {
        instructions.add(t -> t.setX(x));
        return this;
    }

    @Override
    public Turtle setY(double y) {
        instructions.add(t -> t.setY(y));
        return this;
    }

    @Override
    public Turtle setAngle(double a) {
        instructions.add(t -> t.setAngle(a));
        return this;
    }

    @Override
    public Turtle setBrush(Brush brush) {
        instructions.add(t -> t.setBrush(brush));
        return this;
    }

    @Override
    public Turtle setTool(Draggable tool) {
        instructions.add(t -> t.setTool(tool));
        return this;
    }

    @Override
    public Turtle repeat(int i) {
        return new TurtleNested(this, (todo) -> {
            if (todo.isEmpty()) return;
            for (int j = 0; j < i; j++)
                for (Consumer<Turtle> instruction : todo)
                    instruction.accept(this);
        });
    }

    @Override
    public Turtle run(Consumer<Turtle> procedure) {
        instructions.add(t -> t.run(procedure));
        return this;
    }
    
    @Override
    public Turtle center() {
        instructions.add(Turtle::center);
        return this;
    }
    
    // pro tyto metody nemůže být použita výchozí implementace
    // - metody z getterů by zůstaly po celý blok nezměněny
    
    @Override
    public Turtle right(double angle) {
        instructions.add(t -> t.right(angle));
        return this;
    }

    @Override
    public Turtle left(double angle) {
        instructions.add(t -> t.left(angle));
        return this;
    }

    @Override
    public Turtle reverse() {
        instructions.add(Turtle::reverse);
        return this;
    }
    
    // pozor na tyto metody - poskytují data ještě před začítkem bloku
    
    @Override
    public double getAngle() {
        return turtle.getAngle();
    }

    @Override
    public double getX() {
        return turtle.getX();
    }

    @Override
    public double getY() {
        return turtle.getY();
    }
    
    // konec
    
    @Override
    public Turtle end() {
        consumer.accept(instructions);
        return turtle;
    }
    
}